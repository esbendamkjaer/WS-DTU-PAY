package dk.dtu.grp08.payment.domain.services;

import dk.dtu.grp08.payment.data.adapter.bank.BankAdapter;
import dk.dtu.grp08.payment.domain.adapters.IBankAdapter;
import dk.dtu.grp08.payment.domain.events.CustomerBankAccountAssignedEvent;
import dk.dtu.grp08.payment.domain.events.EventType;
import dk.dtu.grp08.payment.domain.events.MerchantBankAccountAssignedEvent;
import dk.dtu.grp08.payment.domain.events.PaymentRequestedEvent;
import dk.dtu.grp08.payment.domain.models.CorrelationId;
import dk.dtu.grp08.payment.domain.models.payment.BankAccountNo;
import dk.dtu.grp08.payment.domain.models.payment.Payment;
import dk.dtu.grp08.payment.domain.models.Token;
import dk.dtu.grp08.payment.domain.repositories.IPaymentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.val;
import messaging.Event;
import messaging.MessageQueue;
import messaging.implementations.RabbitMqQueue;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class PaymentService implements IPaymentService {

    private final IBankAdapter bankAdapter = new BankAdapter();
    private final MessageQueue messageQueue;

    private final IPaymentRepository paymentRepository;

    private final PolicyManager policyManager = new PolicyManager();


    public PaymentService(IPaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
        this.messageQueue = new RabbitMqQueue("localhost");

        this.messageQueue.addHandler(
            EventType.MERCHANT_BANK_ACCOUNT_ASSIGNED.getEventName(),
            this::handleMerchantBankAccountAssigned
        );

        this.messageQueue.addHandler(
            EventType.CUSTOMER_BANK_ACCOUNT_ASSIGNED.getEventName(),
            this::handleCustomBankAccountAssigned
        );
    }

    @Override
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

                this.transferMoney(payment);

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

        this.policyManager.removePolicy(correlationID);

        return paymentResult;
    }

    public void handleMerchantBankAccountAssigned(Event mqEvent) {
        val event = mqEvent.getArgument(0, MerchantBankAccountAssignedEvent.class);

        System.out.println("MerchantBankAccountAssignedEvent");
        System.out.println(event.getCorrelationId().getId());

        CompletableFuture<BankAccountNo> future = policyManager.getPolicyByCorrelationIdAndEvent(
            event.getCorrelationId(),
            EventType.MERCHANT_BANK_ACCOUNT_ASSIGNED,
            BankAccountNo.class
        );

        future.complete(event.getBankAccountNo());
    }

    public void handleCustomBankAccountAssigned(Event mqEvent) {
        val event = mqEvent.getArgument(0, CustomerBankAccountAssignedEvent.class);

        System.out.println("CustomerBankAccountAssignedEvent");

        CompletableFuture<BankAccountNo> future = policyManager.getPolicyByCorrelationIdAndEvent(
            event.getCorrelationId(),
            EventType.CUSTOMER_BANK_ACCOUNT_ASSIGNED,
            BankAccountNo.class
        );

        future.complete(event.getBankAccountNo());
    }


    public void transferMoney(Payment payment) {
        bankAdapter.makeBankTransfer(payment);
        paymentRepository.savePayment(payment);
    }

}
