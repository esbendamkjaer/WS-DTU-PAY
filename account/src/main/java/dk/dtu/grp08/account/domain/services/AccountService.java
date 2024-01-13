package dk.dtu.grp08.account.domain.services;

import dk.dtu.grp08.account.domain.models.user.UserAccount;
import dk.dtu.grp08.account.domain.models.user.UserAccountId;
import dk.dtu.grp08.account.domain.repository.IAccountRepository;
import dk.dtu.grp08.account.domain.models.user.BankAccountNo;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.val;

import java.util.Optional;

@ApplicationScoped
public class AccountService {

    private final IAccountRepository accountRepository;

    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public UserAccount registerAccount(
        String name,
        String cpr,
        BankAccountNo bankAccountNo
    ) {
        return accountRepository.createUserAccount(
            new UserAccount(name, cpr, bankAccountNo)
        );
    }

    public Optional<UserAccount> getUserAccountById(String id) {
        val userAccountId = UserAccountId.fromString(id);
        return accountRepository.findById(userAccountId);
    }

    public Optional<UserAccount> getUserAccountById(UserAccountId id) {
        return accountRepository.findById(id);
    }

}
