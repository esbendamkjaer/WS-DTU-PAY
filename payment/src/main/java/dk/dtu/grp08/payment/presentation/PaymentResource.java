package dk.dtu.grp08.payment.presentation;

import dk.dtu.grp08.payment.domain.events.CustomerBankAccountAssignedEvent;
import dk.dtu.grp08.payment.domain.events.EventType;
import dk.dtu.grp08.payment.domain.events.MerchantBankAccountAssignedEvent;
import dk.dtu.grp08.payment.domain.events.PaymentRequestedEvent;
import dk.dtu.grp08.payment.domain.models.CorrelationId;
import dk.dtu.grp08.payment.domain.models.Token;
import dk.dtu.grp08.payment.domain.models.payment.BankAccountNo;
import dk.dtu.grp08.payment.domain.models.payment.Payment;
import dk.dtu.grp08.payment.domain.services.PaymentService;
import dk.dtu.grp08.payment.presentation.contracts.IPaymentResource;
import lombok.val;
import messaging.Event;
import messaging.MessageQueue;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;


public class PaymentResource implements IPaymentResource {
    private final MessageQueue messageQueue;

    PaymentService paymentService;

    private final Map<CorrelationId, CompletableFuture<Payment>> correlations = new ConcurrentHashMap<>();



    public PaymentResource(MessageQueue messageQueue) {

        this.messageQueue = messageQueue;

        this.messageQueue.addHandler(
                EventType.MERCHANT_BANK_ACCOUNT_ASSIGNED.getEventName(),
                this::handleMerchantBankAccountAssigned
        );

        this.messageQueue.addHandler(
                EventType.CUSTOMER_BANK_ACCOUNT_ASSIGNED.getEventName(),
                this::handleCustomBankAccountAssigned
        );

        paymentService = new PaymentService();
    }


    public void handleCustomBankAccountAssigned(Event mqEvent) {
        val event = mqEvent.getArgument(0, CustomerBankAccountAssignedEvent.class);
        CompletableFuture<Payment> paymentFuture = correlations.get(event.getCorrelationId());
        BankAccountNo bankAccountNo = event.getBankAccountNo();

        paymentService.handleCustomBankAccountAssigned(bankAccountNo, correlations);
    }

    public void handleMerchantBankAccountAssigned(Event mqEvent) {
        val event = mqEvent.getArgument(0, CustomerBankAccountAssignedEvent.class);
        CompletableFuture<Payment> paymentFuture = correlations.get(event.getCorrelationId());
        BankAccountNo bankAccountNo = event.getBankAccountNo();

        paymentService.handleMerchantBankAccountAssigned(bankAccountNo);


    }


    @Override
    public void requestPayment(UUID merchantID, Token token, BigDecimal amount) {
        int collerationID = paymentService.requestPayment(merchantID, token, amount);

        messageQueue.publish(
                new Event(
                        EventType.PAYMENT_REQUESTED.getEventName(),
                        new Object[]{
                                new PaymentRequestedEvent(
                                        merchantID,
                                        token,
                                        amount,
                                        collerationID
                                )
                        }
                )
        );

    }
}
