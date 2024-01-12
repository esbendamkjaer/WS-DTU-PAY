package dk.dtu.grp08.account.presentation;

import dk.dtu.grp08.account.presentation.contracts.IAccountResource;
import dk.dtu.grp08.account.domain.services.AccountService;
import dk.dtu.grp08.account.domain.models.UserAccount;
import jakarta.inject.Inject;
import messaging.MessageQueue;
import messaging.implementations.RabbitMqQueue;

public class AccountResource implements IAccountResource {

    private final AccountService accountService;

    private final MessageQueue messageQueue = new RabbitMqQueue("localhost");

    public AccountResource(AccountService accountService) {
        this.accountService = accountService;


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

    public void

}
