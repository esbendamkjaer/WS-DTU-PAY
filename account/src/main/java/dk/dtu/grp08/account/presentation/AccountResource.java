package dk.dtu.grp08.account.presentation;

import dk.dtu.grp08.account.domain.events.BankAccountNoAssignedEvent;
import dk.dtu.grp08.account.domain.events.EventType;
import dk.dtu.grp08.account.domain.events.TokenInvalidatedEvent;
import dk.dtu.grp08.account.domain.events.TokenValidatedEvent;
import dk.dtu.grp08.account.domain.models.user.BankAccountNo;
import dk.dtu.grp08.account.presentation.contracts.IAccountResource;
import dk.dtu.grp08.account.domain.services.AccountService;
import dk.dtu.grp08.account.domain.models.user.UserAccount;
import io.quarkus.logging.Log;
import messaging.Event;
import messaging.MessageQueue;
import messaging.implementations.RabbitMqQueue;

import java.util.Optional;

public class AccountResource implements IAccountResource {

    private final AccountService accountService;

    private final MessageQueue messageQueue = new RabbitMqQueue("localhost");

    public AccountResource(AccountService accountService) {
        this.accountService = accountService;

        this.messageQueue.addHandler(
            EventType.TOKEN_VALIDATED.getEventName(),
            this::handleTokenValidatedEvent
        );

    }

    @Override
    public UserAccount createUserAccount(UserAccount userAccount) {
        Log.debug("Creating user account: " + userAccount.toString());

        return new UserAccount("test", "1234", new BankAccountNo("1234"));
    }

    @Override
    public Optional<UserAccount> getUserAccount(String id) {
        return accountService.getUserAccountById(id);
    }

    @Override
    public UserAccount[] getAllUserAccounts() {
        return new UserAccount[0];
    }

    public void handleTokenValidatedEvent(Event event) {
        TokenValidatedEvent tokenValidatedEvent = event.getArgument(0, TokenValidatedEvent.class);

        this.accountService.getUserAccountById(
            tokenValidatedEvent.getUserAccountId()
        ).ifPresentOrElse(
            userAccount -> {
                Event bankAccountNoAssignedEvent = new Event(
                    EventType.CUSTOMER_BANK_ACCOUNT_NO_ASSIGNED.getEventName(),
                    new Object[]{
                        new BankAccountNoAssignedEvent(
                            userAccount.getBankAccountNo(),
                            tokenValidatedEvent.getCorrelationId()
                        )
                    }
                );

                this.messageQueue.publish(
                    bankAccountNoAssignedEvent
                );
            },
            () -> {
                Event tokenInvalidatedEvent = new Event(
                    EventType.TOKEN_INVALIDATED.getEventName(),
                    new Object[]{
                        new TokenInvalidatedEvent(
                            tokenValidatedEvent.getToken(),
                            tokenValidatedEvent.getCorrelationId()
                        )
                    }
                );

                this.messageQueue.publish(
                    tokenInvalidatedEvent
                );
            }
        );
    }

}
