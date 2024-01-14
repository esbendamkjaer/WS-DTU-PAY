package dk.dtu.grp08.payment.presentation;

import dk.dtu.grp08.payment.domain.adapters.IBankAdapter;
import dk.dtu.grp08.payment.domain.events.CustomerBankAccountAssignedEvent;
import dk.dtu.grp08.payment.domain.events.EventType;
import dk.dtu.grp08.payment.domain.events.MerchantBankAccountAssignedEvent;
import dk.dtu.grp08.payment.domain.events.PaymentRequestedEvent;
import dk.dtu.grp08.payment.domain.models.CorrelationId;
import dk.dtu.grp08.payment.domain.models.Token;
import dk.dtu.grp08.payment.domain.models.payment.BankAccountNo;
import dk.dtu.grp08.payment.domain.models.payment.Payment;
import dk.dtu.grp08.payment.domain.services.PaymentService;
import dk.dtu.grp08.payment.domain.services.PolicyManager;
import dk.dtu.grp08.payment.presentation.contracts.IPaymentResource;
import lombok.SneakyThrows;
import lombok.val;
import messaging.Event;
import messaging.MessageQueue;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class PaymentRessource implements IPaymentResource {


    private final MessageQueue messageQueue;

    private final PolicyManager policyManager = new PolicyManager();

    private final PaymentService paymentService = new PaymentService();


    public PaymentRessource(MessageQueue messageQueue) {
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

    public void requestPayment(
            final UUID merchantID,
            final Token token,
            final BigDecimal amount
    ) {
        CorrelationId correlationID = CorrelationId.randomId();

        val payment = new Payment();
        payment.setAmount(amount);

        CompletableFuture<BankAccountNo> customerBankAccountAssignedFuture = new CompletableFuture<>();
        CompletableFuture<BankAccountNo> merchantBankAccountAssignedFuture = new CompletableFuture<>();

        CompletableFuture<Payment> paymentFuture = customerBankAccountAssignedFuture.thenCombine(
                merchantBankAccountAssignedFuture,
                (debtor, creditor) -> {
                    paymentService.assignMerchant(payment,debtor);
                    paymentService.assignCustomer(payment,creditor);
                    return payment;
                }
        );

        Map<EventType, CompletableFuture> map = new ConcurrentHashMap<>();
        map.put(EventType.CUSTOMER_BANK_ACCOUNT_ASSIGNED, customerBankAccountAssignedFuture);
        map.put(EventType.MERCHANT_BANK_ACCOUNT_ASSIGNED, merchantBankAccountAssignedFuture);

        policyManager.addPolicy(correlationID, map);

        messageQueue.publish(
                new Event(
                        EventType.PAYMENT_REQUESTED.getEventName(),
                        new Object[]{
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

        this.paymentService.transferMoney(paymentResult);
        this.policyManager.removePolicy(correlationID);


    }

    @SneakyThrows
    public void handleMerchantBankAccountAssigned(Event mqEvent) {
        val event = mqEvent.getArgument(0, MerchantBankAccountAssignedEvent.class);

        CompletableFuture merchantBankAccountAssignedFuture = policyManager.getPolicyByCorrelationIdAndEvent(
                event.getCorrelationId(),
                EventType.MERCHANT_BANK_ACCOUNT_ASSIGNED
        );

        merchantBankAccountAssignedFuture.complete(event.getBankAccountNo());
    }

    @SneakyThrows
    public void handleCustomBankAccountAssigned(Event mqEvent) {
        val event = mqEvent.getArgument(0, CustomerBankAccountAssignedEvent.class);

        CompletableFuture customerBankAccountAssignedFuture = policyManager.getPolicyByCorrelationIdAndEvent(
                event.getCorrelationId(),
                EventType.CUSTOMER_BANK_ACCOUNT_ASSIGNED
        );

        customerBankAccountAssignedFuture.complete(event.getBankAccountNo());
    }


}
