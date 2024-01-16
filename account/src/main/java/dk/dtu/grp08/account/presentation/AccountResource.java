package dk.dtu.grp08.account.presentation;

import dk.dtu.grp08.account.domain.events.*;
import dk.dtu.grp08.account.domain.models.user.BankAccountNo;
import dk.dtu.grp08.account.domain.models.user.UserAccountId;
import dk.dtu.grp08.account.presentation.contracts.IAccountResource;
import dk.dtu.grp08.account.domain.services.AccountService;
import dk.dtu.grp08.account.domain.models.user.UserAccount;
import jakarta.enterprise.context.ApplicationScoped;
import messaging.Event;
import messaging.MessageQueue;
import messaging.implementations.RabbitMqQueue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AccountResource implements IAccountResource {

    private final AccountService accountService;

    public AccountResource(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserAccount createUserAccount(
        UserAccount userAccount
    ) {
        return this.accountService.registerAccount(
            userAccount.getName(),
            userAccount.getCpr(),
            userAccount.getBankAccountNo()
        );
    }

    @Override
    public Optional<UserAccount> getUserAccount(UUID id) {
        return accountService.getUserAccountById(
            new UserAccountId(id)
        );
    }

    @Override
    public List<UserAccount> getAllUserAccounts() {
        return accountService.getUserAccounts();
    }

    @Override
    public void deleteUserAccount(UUID id) {
        this.accountService.deleteUserAccount(
            new UserAccountId(id)
        );
    }
}
