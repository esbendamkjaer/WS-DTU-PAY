package dk.dtu.grp08.account.domain.services;

import dk.dtu.grp08.account.domain.exceptions.NoSuchUserAccountException;
import dk.dtu.grp08.account.domain.models.user.UserAccount;
import dk.dtu.grp08.account.domain.models.user.UserAccountId;
import dk.dtu.grp08.account.domain.repository.IAccountRepository;
import dk.dtu.grp08.account.domain.models.user.BankAccountNo;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.val;

import java.util.Optional;

@ApplicationScoped
public class AccountService implements IAccountService {

    private final IAccountRepository accountRepository;

    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserAccount registerAccount(
        String name,
        String cpr,
        BankAccountNo bankAccountNo
    ) {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(
            UserAccountId.randomId()
        );
        userAccount.setName(name);
        userAccount.setCpr(cpr);
        userAccount.setBankAccountNo(bankAccountNo);

        return accountRepository.createUserAccount(
            userAccount
        );
    }

    @Override
    public Optional<UserAccount> getUserAccountById(UserAccountId id) {
        return accountRepository.findById(id);
    }

    @Override
    public void deleteUserAccount(UserAccountId userAccountId) {
        val userAccount = accountRepository.findById(userAccountId).orElseThrow(
            () -> new NoSuchUserAccountException(
                "No user account with id " + userAccountId.getId()
            )
        );

        accountRepository.deleteUserAccount(
            userAccount.getId()
        );
    }

}
