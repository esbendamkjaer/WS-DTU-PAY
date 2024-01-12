package dk.dtu.grp08.account.domain.services;

import dk.dtu.grp08.account.domain.events.TokenValidatedEvent;
import dk.dtu.grp08.account.domain.models.useraccount.UserAccount;
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
        String firstName,
        String lastName,
        String cpr,
        String bankAccountNo
    ) {
        UserAccount userAccount = new UserAccount(
            firstName,
            lastName,
            cpr,
            bankAccountNo
        );

        return userAccount;
    }

    public Optional<UserAccount> getUserAccountById(String userId) {

    }

    public void handleTokenValidatedEvent(Event event) {
        TokenValidatedEvent tokenValidatedEvent = event.getArgument(0, TokenValidatedEvent.class);

        UserAccount userAccount = this.accountRepository.getUserAccountById(
            tokenValidatedEvent.getUserId()
        ).orElseThrow();


    }

}
