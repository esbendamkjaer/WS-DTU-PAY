package dk.dtu.grp08.customer.domain.services;

import dk.dtu.grp08.customer.domain.events.*;
import dk.dtu.grp08.customer.domain.exceptions.NoSuchUserAccountException;
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

        this.messageQueue.addHandler(
            EventType.ACCOUNT_RETURNED.getEventName(),
            this::handleAccountReturnedEvent
        );

        this.messageQueue.addHandler(
            EventType.USER_NOT_FOUND.getEventName(),
            this::handleUserNotFoundEvent
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

    @Override
    public CompletableFuture<UserAccount> getUserAccount(UserAccountId userAccountId) {
        CorrelationId correlationId = CorrelationId.randomId();

        Policy<UserAccount> policy = new PolicyBuilder<UserAccount>()
                .addPart(
                    AccountReturnedEvent.class
                )
                .setPolicyFunction(
                    (p) -> p.getDependency(
                        AccountReturnedEvent.class,
                        UserAccount.class
                    )
                ).build();

        this.policyManager.addPolicy(
            correlationId,
            policy
        );

        Event accountRequestedEvent = new Event(
            EventType.ACCOUNT_REQUESTED.getEventName(),
            new Object[] {
                new AccountRequestedEvent(
                    correlationId,
                    userAccountId
                )
            }
        );
        this.messageQueue.publish(accountRequestedEvent);

        return policy.getCombinedFuture();
    }

    public void handleAccountReturnedEvent(Event event) {
        AccountReturnedEvent accountReturnedEvent = event.getArgument(0, AccountReturnedEvent.class);

        if (!this.policyManager.hasPolicy(accountReturnedEvent.getCorrelationId())) {
            return;
        }



        this.policyManager.getPolicy(
            accountReturnedEvent.getCorrelationId()
        ).getDependency(
            AccountReturnedEvent.class
        ).complete(
            accountReturnedEvent.getUserAccount()
        );
    }

    public void handleAccountRegisteredEvent(Event event) {
        AccountRegisteredEvent accountRegisteredEvent = event.getArgument(0, AccountRegisteredEvent.class);



        if (!this.policyManager.hasPolicy(accountRegisteredEvent.getCorrelationId())) {
            return;
        }

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



        if (!this.policyManager.hasPolicy(accountDeregisteredEvent.getCorrelationId())) {
            return;
        }

        this.policyManager.getPolicy(
            accountDeregisteredEvent.getCorrelationId()
        ).getDependency(
            AccountDeregisteredEvent.class
        ).complete(null);
    }

    private void handleUserNotFoundEvent(Event event) {
        UserNotFoundEvent userNotFoundEvent = event.getArgument(0, UserNotFoundEvent.class);

        if (!this.policyManager.hasPolicy(userNotFoundEvent.getCorrelationId())) {
            return;
        }



        this.policyManager.getPolicy(
            userNotFoundEvent.getCorrelationId()
        ).getCombinedFuture()
            .completeExceptionally(
                new NoSuchUserAccountException()
            );
    }
}
