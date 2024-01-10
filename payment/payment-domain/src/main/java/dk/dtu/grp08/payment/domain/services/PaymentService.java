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
import lombok.SneakyThrows;
import lombok.val;
import messaging.Event;
import messaging.MessageQueue;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class PaymentService {


    private IBankAdapter bankAdapter;
    private final MessageQueue messageQueue;

    private final Map<CorrelationId, CompletableFuture<Payment>> correlations = new ConcurrentHashMap<>();

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

    public void requestPayment(UUID merchantID, Token token, BigDecimal amount) {
        CorrelationId correlationID = CorrelationId.randomId();

        val payment = new Payment();
        payment.setAmount(amount);

        CompletableFuture<Payment> paymentFuture = new CompletableFuture<>();

        this.correlations.put(correlationID, paymentFuture);

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

    }

    @SneakyThrows
    public void handleMerchantBankAccountAssigned(Event mqEvent) {
        val event = mqEvent.getArgument(0, MerchantBankAccountAssignedEvent.class);
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
        bankAdapter.bankTransfering(payment);
    }


}
