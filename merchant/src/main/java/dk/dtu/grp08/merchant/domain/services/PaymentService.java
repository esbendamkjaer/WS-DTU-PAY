package dk.dtu.grp08.merchant.domain.services;

import dk.dtu.grp08.merchant.domain.events.EventType;
import dk.dtu.grp08.merchant.domain.events.PaymentFailedEvent;
import dk.dtu.grp08.merchant.domain.events.PaymentRequestedEvent;
import dk.dtu.grp08.merchant.domain.events.PaymentTransferredEvent;
import dk.dtu.grp08.merchant.domain.exceptions.PaymentFailedException;
import dk.dtu.grp08.merchant.domain.models.CorrelationId;
import dk.dtu.grp08.merchant.domain.models.Payment;
import dk.dtu.grp08.merchant.domain.models.PaymentRequest;

import dk.dtu.grp08.merchant.domain.policy.Policy;
import dk.dtu.grp08.merchant.domain.policy.PolicyBuilder;
import dk.dtu.grp08.merchant.domain.policy.PolicyManager;
import dk.dtu.grp08.merchant.domain.services.contracts.IPaymentService;
import jakarta.enterprise.context.ApplicationScoped;
import messaging.Event;
import messaging.MessageQueue;

import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class PaymentService implements IPaymentService {

    private final MessageQueue messageQueue;
    private final PolicyManager policyManager;

    /**
     * @author Alexander Matzen (s233475)
     */
    public PaymentService(
        MessageQueue messageQueue,
        PolicyManager policyManager
    ) {
        this.messageQueue = messageQueue;
        this.policyManager = policyManager;

        this.messageQueue.addHandler(
            EventType.PAYMENT_TRANSFERRED.getEventName(),
            this::handlePaymentTransferredEvent
        );

        this.messageQueue.addHandler(
            EventType.PAYMENT_FAILED.getEventName(),
            this::handlePaymentFailedEvent
        );
    }

    /**
     * @author Esben Damkjær Sørensen (s233474)
     */
    @Override
    public CompletableFuture<Payment> createPayment(PaymentRequest paymentRequest) {
        CorrelationId correlationId = CorrelationId.randomId();

        Policy<Payment> paymentPolicy = new PolicyBuilder<Payment>()
            .addPart(
                PaymentTransferredEvent.class
            ).setPolicyFunction(
                (p) -> p.getDependency(
                    PaymentTransferredEvent.class,
                    Payment.class
                )
            ).build();

        this.policyManager.addPolicy(
            correlationId,
            paymentPolicy
        );

        Event paymentRequestedEvent = new Event(
            EventType.PAYMENT_REQUESTED.getEventName(),
                new Object[]{
                    new PaymentRequestedEvent(
                        correlationId,
                        paymentRequest
                    )
                }
        );

        this.messageQueue.publish(
            paymentRequestedEvent
        );

        return paymentPolicy.getCombinedFuture();
    }
    /**
     * @author Fuad Hassan Jama (s233468)
     */
    public void handlePaymentTransferredEvent(Event event) {
        PaymentTransferredEvent paymentTransferredEvent = event.getArgument(0, PaymentTransferredEvent.class);

        if (!this.policyManager.hasPolicy(
            paymentTransferredEvent.getCorrelationId()
        )) {
            return;
        }

        this.policyManager.getPolicy(
            paymentTransferredEvent.getCorrelationId()
        ).getDependency(
            PaymentTransferredEvent.class
        ).complete(
            paymentTransferredEvent.getPayment()
        );
    }

    /**
     * @author Dilara Eda Celepli (s184262)
     */
    public void handlePaymentFailedEvent(Event event) {
        PaymentFailedEvent paymentFailedEvent = event.getArgument(0, PaymentFailedEvent.class);

        if (!this.policyManager.hasPolicy(
            paymentFailedEvent.getCorrelationId()
        )) {
            return;
        }

        this.policyManager.getPolicy(
            paymentFailedEvent.getCorrelationId()
        ).getCombinedFuture()
            .completeExceptionally(
                new PaymentFailedException(
                    paymentFailedEvent.getCause()
                )
            );
    }
}
