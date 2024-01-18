package dk.dtu.grp08.payment.domain.services;

import dk.dtu.grp08.payment.domain.adapters.IBankAdapter;
import dk.dtu.grp08.payment.domain.events.*;
import dk.dtu.grp08.payment.domain.exceptions.InvalidTokenException;
import dk.dtu.grp08.payment.domain.models.PaymentRequest;
import dk.dtu.grp08.payment.domain.models.Token;
import dk.dtu.grp08.payment.domain.models.payment.BankAccountNo;
import dk.dtu.grp08.payment.domain.models.payment.Payment;
import dk.dtu.grp08.payment.domain.repositories.IPaymentRepository;
import dk.dtu.grp08.payment.domain.util.policy.Policy;
import dk.dtu.grp08.payment.domain.util.policy.PolicyBuilder;
import dk.dtu.grp08.payment.domain.util.policy.PolicyManager;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.val;
import messaging.Event;
import messaging.MessageQueue;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Startup
@ApplicationScoped
public class PaymentService implements IPaymentService {

    private final IBankAdapter bankAdapter;
    private final MessageQueue messageQueue;
    private final IPaymentRepository paymentRepository;

    private final PolicyManager policyManager;


    public PaymentService(
        IPaymentRepository paymentRepository,
        MessageQueue messageQueue,
        IBankAdapter bankAdapter,
        PolicyManager policyManager
    ) {
        this.paymentRepository = paymentRepository;
        this.messageQueue = messageQueue;
        this.bankAdapter = bankAdapter;
        this.policyManager = policyManager;

        this.messageQueue.addHandler(
            EventType.MERCHANT_BANK_ACCOUNT_ASSIGNED.getEventName(),
            this::handleMerchantBankAccountAssigned
        );

        this.messageQueue.addHandler(
            EventType.CUSTOMER_BANK_ACCOUNT_ASSIGNED.getEventName(),
            this::handleCustomerBankAccountAssigned
        );

        this.messageQueue.addHandler(
            EventType.TOKEN_INVALIDATED.getEventName(),
            this::handleTokenInvalidatedEvent
        );

        this.messageQueue.addHandler(
            EventType.PAYMENT_REQUESTED.getEventName(),
            this::handlePaymentRequestedEvent
        );
    }

    @Override
    public Policy<PaymentTransferredEvent> initiatePayment(
        PaymentRequestedEvent paymentRequestedEvent
    ) {
        Policy<PaymentTransferredEvent> paymentPolicy = new PolicyBuilder<PaymentTransferredEvent>()
            .addPart(CustomerBankAccountAssignedEvent.class)
            .addPart(MerchantBankAccountAssignedEvent.class)
            .setPolicyFunction(
                (p) -> {
                    PaymentRequest paymentRequest = paymentRequestedEvent.getPaymentRequest();

                    Payment payment = new Payment();
                    payment.setAmount(
                        paymentRequest.getAmount()
                    );

                    CustomerBankAccountAssignedEvent customerBankAccountAssignedEvent = p.getDependency(
                        CustomerBankAccountAssignedEvent.class,
                        CustomerBankAccountAssignedEvent.class
                    );

                    MerchantBankAccountAssignedEvent merchantBankAccountAssignedEvent = p.getDependency(
                        MerchantBankAccountAssignedEvent.class,
                        MerchantBankAccountAssignedEvent.class
                    );

                    payment.setDebtor(
                        customerBankAccountAssignedEvent.getBankAccountNo()
                    );

                    payment.setCreditor(
                        merchantBankAccountAssignedEvent.getBankAccountNo()
                    );


                    payment = this.makePayment(
                        payment,
                        paymentRequest.getMerchantId(),
                        paymentRequest.getToken()
                    );

                    PaymentTransferredEvent paymentTransferredEvent = new PaymentTransferredEvent(
                        paymentRequestedEvent.getCorrelationId(),
                        payment,
                        merchantBankAccountAssignedEvent.getUserId(),
                        customerBankAccountAssignedEvent.getUserId()
                    );




                    messageQueue.publish(
                        new Event(
                            EventType.PAYMENT_TRANSFERRED.getEventName(),
                            new Object[] {
                                paymentTransferredEvent
                            }
                        )
                    );

                    return paymentTransferredEvent;
                }
            ).build();

        paymentPolicy.getCombinedFuture().exceptionally(
            (e) -> {

                Event paymentFailedEvent = new Event(
                    EventType.PAYMENT_FAILED.getEventName(),
                    new Object[] {
                        new PaymentFailedEvent(
                            paymentRequestedEvent.getCorrelationId(),
                            e.getMessage()
                        )
                    }
                );

                messageQueue.publish(
                    paymentFailedEvent
                );

                return null;
            }
        );

        return paymentPolicy;
    }

    public void handleMerchantBankAccountAssigned(Event mqEvent) {
        val event = mqEvent.getArgument(0, MerchantBankAccountAssignedEvent.class);

        if (!policyManager.hasPolicy(event.getCorrelationId())) {
            return;
        }




        CompletableFuture<MerchantBankAccountAssignedEvent> future = policyManager.getPolicy(
            event.getCorrelationId()
        ).getDependency(
            MerchantBankAccountAssignedEvent.class
        );

        future.complete(event);
    }

    public void handleCustomerBankAccountAssigned(Event mqEvent) {
        val event = mqEvent.getArgument(0, CustomerBankAccountAssignedEvent.class);

        if (!policyManager.hasPolicy(event.getCorrelationId())) {
            return;
        }



        CompletableFuture<CustomerBankAccountAssignedEvent> future = policyManager.getPolicy(
            event.getCorrelationId()
        ).getDependency(
            CustomerBankAccountAssignedEvent.class
        );

        future.complete(event);
    }

    public void handleTokenInvalidatedEvent(Event mqEvent) {
        val tokenInvalidatedEvent = mqEvent.getArgument(0, TokenInvalidatedEvent.class);



        CompletableFuture<?> future = policyManager.getPolicy(
            tokenInvalidatedEvent.getCorrelationId()
        ).getCombinedFuture();

        future.completeExceptionally(
            new InvalidTokenException()
        );
    }


    public Payment makePayment(Payment payment, UUID merchantID, Token token) {
        bankAdapter.makeBankTransfer(payment);

        return paymentRepository.savePayment(payment);
    }

    public void handlePaymentRequestedEvent(Event mqEvent) {
        val event = mqEvent.getArgument(0, PaymentRequestedEvent.class);


        PaymentRequest paymentRequest = event.getPaymentRequest();

        Policy<PaymentTransferredEvent> paymentPolicy = this.initiatePayment(
            event
        );

        this.policyManager.addPolicy(
            event.getCorrelationId(),
            paymentPolicy
        );

        Event paymentInitiatedEvent = new Event(
            EventType.PAYMENT_INITIATED.getEventName(),
            new Object[] {
                new PaymentInitiatedEvent(
                    paymentRequest.getMerchantId(),
                    paymentRequest.getToken(),
                    paymentRequest.getAmount(),
                    event.getCorrelationId()
                )
            }
        );


        messageQueue.publish(
            paymentInitiatedEvent
        );
    }

}
