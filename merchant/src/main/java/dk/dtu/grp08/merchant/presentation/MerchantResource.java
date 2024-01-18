package dk.dtu.grp08.merchant.presentation;

import dk.dtu.grp08.merchant.domain.events.EventType;
import dk.dtu.grp08.merchant.domain.events.MerchantReportRequested;
import dk.dtu.grp08.merchant.domain.events.ReportGenerated;
import dk.dtu.grp08.merchant.domain.models.*;

import dk.dtu.grp08.merchant.domain.services.contracts.IAccountService;
import dk.dtu.grp08.merchant.domain.services.contracts.IPaymentService;
import dk.dtu.grp08.merchant.presentation.contracts.IMerchantResource;
import dk.dtu.grp08.merchant.domain.policy.Policy;
import dk.dtu.grp08.merchant.domain.policy.PolicyBuilder;
import dk.dtu.grp08.merchant.domain.policy.PolicyManager;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.val;
import messaging.Event;
import messaging.MessageQueue;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class MerchantResource implements IMerchantResource {

    private final IAccountService accountService;
    private final IPaymentService paymentService;

    private final MessageQueue messageQueue;

    private final PolicyManager policyManager;

    public MerchantResource(
        IAccountService accountService,
        IPaymentService paymentService,
        MessageQueue messageQueue,
        PolicyManager policyManager
    ) {
        this.accountService = accountService;
        this.paymentService = paymentService;

        this.messageQueue = messageQueue;
        this.policyManager = policyManager;


        this.messageQueue.addHandler(
                EventType.REPORT_GENERATED.getEventName(),
                this::handleReportGenerated
        );
    }

    @Override
    public CompletableFuture<UserAccount> createMerchant(UserAccount userAccount) {
        return this.accountService.createUserAccount(
            userAccount
        );
    }

    @Override
    public CompletableFuture<Void> deleteMerchant(UUID userId) {
        return this.accountService.deleteUserAccount(
            new UserId(userId)
        );
    }

    @Override
    public CompletableFuture<Payment> createPayment(PaymentRequest paymentRequest) {
        return this.paymentService.createPayment(
            paymentRequest
        );
    }




    @Override
    public CompletableFuture<List<Payment>> getReport(
            final UUID userID
    ) {
        CorrelationId correlationID = CorrelationId.randomId();

        Policy<List<Payment>> policy = new PolicyBuilder<List<Payment>>()
                .addPart(ReportGenerated.class)
                .setPolicyFunction(
                        (p) -> {
                            List<Payment> report = p.getDependency(ReportGenerated.class, List.class);

                            return report;
                        }
                ).build();

        policyManager.addPolicy(correlationID, policy);

        messageQueue.publish(
                new Event(
                        EventType.MERCHANT_REPORT_REQUESTED.getEventName(),
                        new Object[]{
                                new MerchantReportRequested(
                                        new UserId(userID),
                                        correlationID
                                )
                        }
                )
        );

        return policy.getCombinedFuture();
    }


    public void handleReportGenerated(Event mqEvent) {
        val event = mqEvent.getArgument(0, ReportGenerated.class);

        if (!this.policyManager.hasPolicy(event.getCorrelationId())) {
            return;
        }



        CompletableFuture<List<Payment>> future = policyManager.getPolicy(
                event.getCorrelationId()
        ).getDependency(
                ReportGenerated.class
        );

        future.complete(event.getReport());
    }
}
