package dk.dtu.grp08.payment.domain.services;

import dk.dtu.grp08.payment.domain.adapters.IBankAdapter;
import dk.dtu.grp08.payment.domain.events.*;
import dk.dtu.grp08.payment.domain.exceptions.InvalidTokenException;
import dk.dtu.grp08.payment.domain.models.CorrelationId;
import dk.dtu.grp08.payment.domain.models.Token;
import dk.dtu.grp08.payment.domain.models.payment.BankAccountNo;
import dk.dtu.grp08.payment.domain.models.payment.Payment;
import dk.dtu.grp08.payment.domain.repositories.IPaymentRepository;
import dk.dtu.grp08.payment.domain.util.policy.Policy;
import dk.dtu.grp08.payment.domain.util.policy.PolicyBuilder;
import dk.dtu.grp08.payment.domain.util.policy.PolicyManager;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.val;
import messaging.Event;
import messaging.MessageQueue;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class PaymentService implements IPaymentService {

    private final IBankAdapter bankAdapter;
    private final MessageQueue messageQueue;
    private final IPaymentRepository paymentRepository;

    private final PolicyManager policyManager = new PolicyManager();


    public PaymentService(
        IPaymentRepository paymentRepository,
        MessageQueue messageQueue,
        IBankAdapter bankAdapter
    ) {
        this.paymentRepository = paymentRepository;
        this.messageQueue = messageQueue;
        this.bankAdapter = bankAdapter;

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
    }

    @Override
    public CompletableFuture<Payment> requestPayment(
        final UUID merchantID,
        final Token token,
        final BigDecimal amount
    ) {
        CorrelationId correlationID = CorrelationId.randomId();

        val payment = new Payment();
        payment.setAmount(amount);

        Policy<Payment> policy = new PolicyBuilder<Payment>()
                .addPart(CustomerBankAccountAssignedEvent.class)
                .addPart(MerchantBankAccountAssignedEvent.class)
                .setPolicyFunction(
                    (p) -> {
                        payment.setDebtor(
                            p.getDependency(
                                CustomerBankAccountAssignedEvent.class,
                                BankAccountNo.class
                            )
                        );
                        payment.setCreditor(
                            p.getDependency(
                                MerchantBankAccountAssignedEvent.class,
                                BankAccountNo.class
                            )
                        );

                        this.transferMoney(
                            payment,
                            merchantID,
                            token
                        );

                        return payment;
                    }
                ).build();

        policyManager.addPolicy(correlationID, policy);

        messageQueue.publish(
            new Event(
                EventType.PAYMENT_INITIATED.getEventName(),
                new Object[] {
                    new PaymentInitiatedEvent(
                        merchantID,
                        token,
                        amount,
                        correlationID
                    )
                }
            )
        );

        return policy.getCombinedFuture();
    }

    public void handleMerchantBankAccountAssigned(Event mqEvent) {
        val event = mqEvent.getArgument(0, MerchantBankAccountAssignedEvent.class);

        System.out.println("MerchantBankAccountAssignedEvent");
        System.out.println(event.getCorrelationId().getId());

        CompletableFuture<BankAccountNo> future = policyManager.getPolicy(
            event.getCorrelationId()
        ).getDependency(
            MerchantBankAccountAssignedEvent.class
        );

        future.complete(event.getBankAccountNo());
    }

    public void handleCustomerBankAccountAssigned(Event mqEvent) {
        val event = mqEvent.getArgument(0, CustomerBankAccountAssignedEvent.class);

        System.out.println("CustomerBankAccountAssignedEvent");

        CompletableFuture<BankAccountNo> future = policyManager.getPolicy(
            event.getCorrelationId()
        ).getDependency(
            CustomerBankAccountAssignedEvent.class
        );

        future.complete(event.getBankAccountNo());
    }

    public void handleTokenInvalidatedEvent(Event mqEvent) {
        val tokenInvalidatedEvent = mqEvent.getArgument(0, TokenInvalidatedEvent.class);

        System.out.println("TokenInvalidatedEvent");

        CompletableFuture<?> future = policyManager.getPolicy(
            tokenInvalidatedEvent.getCorrelationId()
        ).getCombinedFuture();

        future.completeExceptionally(
            new InvalidTokenException()
        );

        Event paymentCanceledEvent = new Event(
            EventType.PAYMENT_CANCELED.getEventName(),
            new Object[] {
                new PaymentCanceledEvent(
                    tokenInvalidatedEvent.getCorrelationId()
                )
            }
        );

        messageQueue.publish(
            paymentCanceledEvent
        );
    }


    public void transferMoney(Payment payment, UUID merchantID, Token token ) {
        //Transfer money event
        bankAdapter.makeBankTransfer(payment);

        paymentRepository.savePayment(payment);

        messageQueue.publish(
                new Event(
                        EventType.PAYMENT_TRANSFERRED.getEventName(),
                        new Object[] {
                                new PaymentTransferredEvent(
                                        merchantID,
                                        token,
                                        payment.getAmount()
                                )
                        }
                )
        );
    }

}
