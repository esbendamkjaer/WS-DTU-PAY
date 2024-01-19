package dk.dtu.grp08.account.domain.services;

import dk.dtu.grp08.account.domain.models.user.BankAccountNo;
import dk.dtu.grp08.account.domain.models.user.UserAccount;
import dk.dtu.grp08.account.domain.models.user.UserAccountId;

import java.util.List;
import java.util.Optional;

public interface IAccountService {

    /**
     * @author Clair Norah Mutebi (s184187)
     */
    UserAccount registerAccount(
        String name,
        String cpr,
        BankAccountNo bankAccountNo
    );

    /**
     * @author Muhamad Hussein Nadali (s233479)
     */
    Optional<UserAccount> getUserAccountById(UserAccountId id);

    /**
     * @author Alexander Matzen (s233475)
     */
    void deleteUserAccount(UserAccountId userAccountId);

    /**
     * @author Fuad Hassan Jama (s233468)
     */
    List<UserAccount> getUserAccounts();
}
