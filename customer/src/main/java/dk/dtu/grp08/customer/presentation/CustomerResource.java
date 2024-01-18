package dk.dtu.grp08.customer.presentation;

import dk.dtu.grp08.customer.domain.models.*;
import dk.dtu.grp08.customer.domain.models.events.CustomerReportRequested;
import dk.dtu.grp08.customer.domain.models.events.EventType;
import dk.dtu.grp08.customer.domain.models.events.ReportGenerated;
import dk.dtu.grp08.customer.domain.policy.Policy;
import dk.dtu.grp08.customer.domain.policy.PolicyBuilder;
import dk.dtu.grp08.customer.domain.policy.PolicyManager;
import dk.dtu.grp08.customer.domain.services.contracts.IAccountService;
import dk.dtu.grp08.customer.domain.services.contracts.ITokenService;
import dk.dtu.grp08.customer.presentation.contracts.ICustomerResource;
import dk.dtu.grp08.customer.presentation.contracts.ITokenAPI;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.val;
import messaging.Event;
import messaging.MessageQueue;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class CustomerResource implements ICustomerResource {

    private final ITokenService tokenService;

    private final IAccountService accountService;

    private final MessageQueue messageQueue;

    private final PolicyManager policyManager;

    public CustomerResource(
            IAccountService accountService,
            ITokenService tokenService,
            MessageQueue messageQueue
    ) {
        this.accountService = accountService;
        this.tokenService = tokenService;

        this.messageQueue = messageQueue;

        policyManager = new PolicyManager();


        this.messageQueue.addHandler(
                EventType.REPORT_GENERATED.getEventName(),
                this::handleReportGenerated
        );
    }

    @Override
    public CompletableFuture<List<Token>> getTokens(
            UUID userId,
            int count
    ) {
        return tokenService.getTokens(
                count,
                new UserAccountId(userId)
        );
    }

    @Override
    public CompletableFuture<UserAccount> createCustomer(
            UserAccount userAccount
    ) {
        return this.accountService.createUserAccount(
                userAccount
        );
    }

    @Override
    public CompletableFuture<Void> deleteCustomer(UUID userId) {
        return this.accountService.deleteUserAccount(
                new UserAccountId(userId)
        );
    }

    @Override
    public CompletableFuture<UserAccount> getCustomer(UUID userId) {
        return this.accountService.getUserAccount(
                new UserAccountId(userId)
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
                        EventType.CUSTOMER_REPORT_REQUESTED.getEventName(),
                        new Object[]{
                                new CustomerReportRequested(
                                        new UserAccountId(userID),
                                        correlationID
                                )
                        }
                )
        );

        return policy.getCombinedFuture();
    }


    public void handleReportGenerated(Event mqEvent) {
        val event = mqEvent.getArgument(0, ReportGenerated.class);

        System.out.println("CustomerBankAccountAssignedEvent");

        CompletableFuture<List<Payment>> future = policyManager.getPolicy(
                event.getCorrelationId()
        ).getDependency(
                ReportGenerated.class
        );

        future.complete(event.getReport());
    }



}
