package dk.dtu.grp08.customer.domain.services;

import dk.dtu.grp08.customer.domain.events.*;
import dk.dtu.grp08.customer.domain.models.CorrelationId;
import dk.dtu.grp08.customer.domain.models.UserAccount;
import dk.dtu.grp08.customer.domain.models.UserAccountId;
import dk.dtu.grp08.customer.domain.policy.Policy;
import dk.dtu.grp08.customer.domain.policy.PolicyBuilder;
import dk.dtu.grp08.customer.domain.policy.PolicyManager;
import dk.dtu.grp08.customer.domain.services.contracts.IAccountService;
import jakarta.enterprise.context.ApplicationScoped;
import messaging.Event;
import messaging.MessageQueue;

import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class AccountService implements IAccountService {

    private final MessageQueue messageQueue;
    private final PolicyManager policyManager;

    public AccountService(
        MessageQueue messageQueue,
        PolicyManager policyManager
    ) {
        this.messageQueue = messageQueue;
        this.policyManager = policyManager;

        this.messageQueue.addHandler(
            EventType.ACCOUNT_REGISTERED.getEventName(),
            this::handleAccountRegisteredEvent
        );

        this.messageQueue.addHandler(
            EventType.ACCOUNT_DEREGISTERED.getEventName(),
            this::handleAccountDeregisteredEvent
        );
    }

    @Override
    public CompletableFuture<Void> deleteUserAccount(UserAccountId userId) {
        CorrelationId correlationId = CorrelationId.randomId();

        Policy<Void> policy = new PolicyBuilder<Void>()
                .addPart(
                    AccountDeregisteredEvent.class
                ).build();

        this.policyManager.addPolicy(
            correlationId,
            policy
        );

        Event deregistrationRequestedEvent = new Event(
            EventType.ACCOUNT_DEREGISTRATION_REQUESTED.getEventName(),
            new Object[] {
                new AccountDeregistrationRequestedEvent(
                    correlationId,
                    userId
                )
            }
        );
        this.messageQueue.publish(deregistrationRequestedEvent);

        return policy.getCombinedFuture();
    }

    @Override
    public CompletableFuture<UserAccount> createUserAccount(UserAccount userAccount) {
        CorrelationId correlationId = CorrelationId.randomId();

        Policy<UserAccount> policy = new PolicyBuilder<UserAccount>()
                .addPart(
                        AccountRegisteredEvent.class
                )
                .setPolicyFunction(
                        (p) -> p.getDependency(
                            AccountRegisteredEvent.class,
                            UserAccount.class
                        )
                ).build();

        this.policyManager.addPolicy(
            correlationId,
            policy
        );

        Event registrationRequestedEvent = new Event(
            EventType.ACCOUNT_REGISTRATION_REQUESTED.getEventName(),
            new Object[] {
                new AccountRegistrationRequestedEvent(
                    correlationId,
                    userAccount
                )
            }
        );
        this.messageQueue.publish(registrationRequestedEvent);

        return policy.getCombinedFuture();
    }

    public void handleAccountRegisteredEvent(Event event) {
        AccountRegisteredEvent accountRegisteredEvent = event.getArgument(0, AccountRegisteredEvent.class);

        this.policyManager.getPolicy(
            accountRegisteredEvent.getCorrelationId()
        ).getDependency(
            AccountRegisteredEvent.class
        ).complete(
            accountRegisteredEvent.getUserAccount()
        );
    }

    public void handleAccountDeregisteredEvent(Event event) {
        AccountDeregisteredEvent accountDeregisteredEvent = event.getArgument(0, AccountDeregisteredEvent.class);

        this.policyManager.getPolicy(
            accountDeregisteredEvent.getCorrelationId()
        ).getDependency(
            AccountDeregisteredEvent.class
        ).complete(null);
    }
}
