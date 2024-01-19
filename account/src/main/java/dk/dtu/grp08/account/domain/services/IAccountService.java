package dk.dtu.grp08.account.domain.services;

import dk.dtu.grp08.account.domain.models.user.BankAccountNo;
import dk.dtu.grp08.account.domain.models.user.UserAccount;
import dk.dtu.grp08.account.domain.models.user.UserAccountId;

import java.util.List;
import java.util.Optional;

public interface IAccountService {

    /**
     * @author Clair
     */
    UserAccount registerAccount(
        String name,
        String cpr,
        BankAccountNo bankAccountNo
    );

    /**
     * @author Muhamad
     */
    Optional<UserAccount> getUserAccountById(UserAccountId id);

    /**
     * @author Alexander
     */
    void deleteUserAccount(UserAccountId userAccountId);

    /**
     * @author Fuad
     */
    List<UserAccount> getUserAccounts();
}
