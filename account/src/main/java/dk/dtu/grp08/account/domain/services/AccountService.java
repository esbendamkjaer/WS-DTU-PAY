package dk.dtu.grp08.account.domain.services;

import dk.dtu.grp08.account.domain.events.TokenValidatedEvent;
import dk.dtu.grp08.account.domain.models.useraccount.BankAccountNo;
import dk.dtu.grp08.account.domain.models.useraccount.UserAccount;
import dk.dtu.grp08.account.domain.models.useraccount.UserId;
import dk.dtu.grp08.account.domain.repository.IAccountRepository;
import jakarta.enterprise.context.ApplicationScoped;
import messaging.Event;

import java.util.Optional;

@ApplicationScoped
public class AccountService {

    private final IAccountRepository accountRepository;

    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public UserAccount registerAccount(
        String name
        String cpr,
        String bankAccountNo
    ) {
        UserAccount userAccount = new UserAccount(
            name,
            cpr,
            new BankAccountNo(bankAccountNo)
        );

        return userAccount;
    }

    public Optional<UserAccount> getUserAccountById(UserId userId) {
        return this.accountRepository
                .getUserAccountById(userId);
    }

}
