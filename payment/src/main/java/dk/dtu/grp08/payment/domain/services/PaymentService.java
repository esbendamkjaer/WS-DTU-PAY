package dk.dtu.grp08.payment.domain.services;

import dk.dtu.grp08.payment.domain.adapters.IBankAdapter;
import dk.dtu.grp08.payment.domain.events.CustomerBankAccountAssignedEvent;
import dk.dtu.grp08.payment.domain.events.EventType;
import dk.dtu.grp08.payment.domain.events.MerchantBankAccountAssignedEvent;
import dk.dtu.grp08.payment.domain.events.PaymentRequestedEvent;
import dk.dtu.grp08.payment.domain.models.CorrelationId;
import dk.dtu.grp08.payment.domain.models.payment.BankAccountNo;
import dk.dtu.grp08.payment.domain.models.payment.Payment;
import dk.dtu.grp08.payment.domain.models.Token;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.SneakyThrows;
import lombok.val;
import messaging.Event;
import messaging.MessageQueue;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class PaymentService implements IPaymentService {

    private IBankAdapter bankAdapter;
    private final MessageQueue messageQueue;

    private final PolicyManager policyManager = new PolicyManager();

    private final Map<CorrelationId, CompletableFuture<Payment>> correlations = new ConcurrentHashMap<>();

    private final Map<CorrelationId, Map<EventType, CompletableFuture<Object>>> corr = new ConcurrentHashMap<>();

    public PaymentService(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;

        this.messageQueue.addHandler(
            EventType.MERCHANT_BANK_ACCOUNT_ASSIGNED.getEventName(),
            this::handleMerchantBankAccountAssigned
        );

        this.messageQueue.addHandler(
            EventType.CUSTOMER_BANK_ACCOUNT_ASSIGNED.getEventName(),
            this::handleCustomBankAccountAssigned
        );
    }

    public Payment requestPayment(
        final UUID merchantID,
        final Token token,
        final BigDecimal amount
    ) {
        CorrelationId correlationID = CorrelationId.randomId();

        val payment = new Payment();
        payment.setAmount(amount);

        CompletableFuture<BankAccountNo> debtorFuture = new CompletableFuture<>();
        CompletableFuture<BankAccountNo> creditorFuture = new CompletableFuture<>();

        CompletableFuture<Payment> paymentFuture = debtorFuture.thenCombine(
            creditorFuture,
            (debtor, creditor) -> {
                payment.setDebtor(debtor);
                payment.setCreditor(creditor);
                return payment;
            }
        );

        Map<EventType, CompletableFuture> map = new ConcurrentHashMap<>();
        map.put(EventType.CUSTOMER_BANK_ACCOUNT_ASSIGNED, debtorFuture);
        map.put(EventType.MERCHANT_BANK_ACCOUNT_ASSIGNED, creditorFuture);
        policyManager.addPolicy(correlationID, map);

        messageQueue.publish(
            new Event(
                EventType.PAYMENT_REQUESTED.getEventName(),
                new Object[] {
                    new PaymentRequestedEvent(
                        merchantID,
                        token,
                        amount,
                        correlationID
                    )
                }
            )
        );

        Payment paymentResult = paymentFuture.join();

        this.corr.remove(correlationID);



        return paymentResult;
    }

    @SneakyThrows
    public void handleMerchantBankAccountAssigned(Event mqEvent) {
        val event = mqEvent.getArgument(0, MerchantBankAccountAssignedEvent.class);

        Map<EventType, CompletableFuture<BankAccountNo>> map;

        CompletableFuture<BankAccountNo> future = policyManager.getPolicyByCorrelationIdAndEvent(
            event.getCorrelationId(),
            EventType.MERCHANT_BANK_ACCOUNT_ASSIGNED,
            BankAccountNo.class
        );

        future.complete(event.getBankAccountNo());

        CompletableFuture<Payment> paymentFuture = correlations.get(event.getCorrelationId());
        BankAccountNo bankAccountNo = event.getBankAccountNo();

        Payment payment = paymentFuture.get();
        payment.setCreditor(bankAccountNo);

        if (payment.getCreditor() != null && payment.getDebtor() != null){
            transferMoney(payment);
        }
    }

    @SneakyThrows
    public void handleCustomBankAccountAssigned(Event mqEvent) {
        val event = mqEvent.getArgument(0, CustomerBankAccountAssignedEvent.class);
        CompletableFuture<Payment> paymentFuture = correlations.get(event.getCorrelationId());
        BankAccountNo bankAccountNo = event.getBankAccountNo();

        Payment payment = paymentFuture.get();
        payment.setDebtor(bankAccountNo);

        if (payment.getCreditor() != null && payment.getDebtor() != null){
            transferMoney(payment);
        }
    }

    public void transferMoney(Payment payment) {
        bankAdapter.makeBankTransfer(payment);
    }


}
