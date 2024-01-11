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
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class PaymentService {


    private IBankAdapter bankAdapter;


    private final Map<CorrelationId, CompletableFuture<Payment>> correlations = new ConcurrentHashMap<>();

    public PaymentService() {
    }

    public void requestPayment(UUID merchantID, Token token, BigDecimal amount) {
        CorrelationId correlationID = CorrelationId.randomId();

        val payment = new Payment();
        payment.setAmount(amount);

        CompletableFuture<Payment> paymentFuture = new CompletableFuture<>();

        this.correlations.put(correlationID, paymentFuture);



       // Payment paymentResult = paymentFuture.join();

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
            correlations.remove(event.getCorrelationId());
        }
    }

    @SneakyThrows
    public void handleCustomBankAccountAssigned(BankAccountNo bankAccountNo, CorrelationId correlationId) {


        Payment payment = paymentFuture.get();
        payment.setDebtor(bankAccountNo);

        if (payment.getCreditor() != null && payment.getDebtor() != null){
            transferMoney(payment);
            correlations.remove(event.getCorrelationId());
        }
    }

    public void transferMoney(Payment payment) {
        bankAdapter.makeBankTransfer(payment);
    }


}
