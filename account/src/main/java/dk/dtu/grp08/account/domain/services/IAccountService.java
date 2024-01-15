package dk.dtu.grp08.account.domain.services;

import dk.dtu.grp08.account.domain.models.user.BankAccountNo;
import dk.dtu.grp08.account.domain.models.user.UserAccount;
import dk.dtu.grp08.account.domain.models.user.UserAccountId;

import java.util.List;
import java.util.Optional;

public interface IAccountService {

    UserAccount registerAccount(
        String name,
        String cpr,
        BankAccountNo bankAccountNo
    );

    Optional<UserAccount> getUserAccountById(UserAccountId id);

    void deleteUserAccount(UserAccountId userAccountId);

    List<UserAccount> getUserAccounts();
}
