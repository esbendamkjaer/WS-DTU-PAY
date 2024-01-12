package dk.dtu.grp08.account.presentation;

import dk.dtu.grp08.account.domain.events.EventType;
import dk.dtu.grp08.account.presentation.contracts.IAccountResource;
import dk.dtu.grp08.account.domain.services.AccountService;
import dk.dtu.grp08.account.domain.models.useraccount.UserAccount;
import messaging.MessageQueue;
import messaging.implementations.RabbitMqQueue;

public class AccountResource implements IAccountResource {

    private final AccountService accountService;

    private final MessageQueue messageQueue = new RabbitMqQueue("localhost");

    public AccountResource(AccountService accountService) {
        this.accountService = accountService;

        this.messageQueue.addHandler(
            EventType.TOKEN_VALIDATED.getEventName(),
            accountService::handleTokenValidatedEvent
        );

    }

    @Override
    public UserAccount createUserAccount(UserAccount userAccount) {
        return accountService.registerAccount(
            userAccount.getFirstName(),
            userAccount.getLastName(),
            userAccount.getCpr(),
            userAccount.getBankAccountNo()
        );
    }

}
