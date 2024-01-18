package dk.dtu.grp08.customer.presentation;

import dk.dtu.grp08.customer.domain.models.*;
import dk.dtu.grp08.customer.domain.models.events.ReportGenerated;
import dk.dtu.grp08.customer.domain.policy.Policy;
import dk.dtu.grp08.customer.domain.policy.PolicyBuilder;
import dk.dtu.grp08.customer.domain.policy.PolicyManager;
import dk.dtu.grp08.customer.presentation.contracts.IAccountAPI;
import dk.dtu.grp08.customer.presentation.contracts.ICustomerResource;
import dk.dtu.grp08.customer.presentation.contracts.ITokenAPI;
import dk.dtu.grp08.customer.domain.models.events.CustomerReportRequested;
import dk.dtu.grp08.customer.domain.models.events.EventType;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.val;
import messaging.Event;
import messaging.MessageQueue;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class CustomerResource implements ICustomerResource {

    @RestClient
    private ITokenAPI tokenResource;

    @RestClient
    private IAccountAPI accountResource;

    private final MessageQueue messageQueue;

    private final PolicyManager policyManager = new PolicyManager();


    public CustomerResource(MessageQueue messageQueue) {

        this.messageQueue = messageQueue;


        this.messageQueue.addHandler(
                EventType.REPORT_GENERATED.getEventName(),
                this::handleReportGenerated
        );
    }

    @Override
    public List<Token> getTokens(
            UUID userId,
            int count
    ) {
        return tokenResource.getTokens(
                count,
                userId
        );
    }

    @Override
    public UserAccount createCustomer(
            UserAccount userAccount
    ) {
        return this.accountResource.createUserAccount(
                userAccount
        );
    }

    @Override
    public void deleteCustomer(UUID userId) {
        this.accountResource.deleteUserAccount(
                userId
        );
    }

    @Override
    public UserAccount getCustomer(UUID userId) {
        return this.accountResource.getUserAccount(userId)
                .orElse(null);
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
