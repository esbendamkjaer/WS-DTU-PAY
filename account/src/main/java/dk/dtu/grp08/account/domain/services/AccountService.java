package dk.dtu.grp08.account.domain.services;

import dk.dtu.grp08.account.domain.models.UserAccount;
import dk.dtu.grp08.account.domain.repository.IAccountRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AccountService {
    @Inject
    IAccountRepository accountRepository;

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

    public UserAccount getUserAccountById(String id) {
        return accountRepository.findById(id);
    }

}
