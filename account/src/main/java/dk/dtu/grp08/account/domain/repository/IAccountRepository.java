package dk.dtu.grp08.account.domain.repository;

import dk.dtu.grp08.account.domain.models.user.UserAccount;
import dk.dtu.grp08.account.domain.models.user.UserAccountId;

import java.util.List;
import java.util.Optional;

public interface IAccountRepository {

    /**
     * @author Esben Damkjær Sørensen (s233474)
     */
    UserAccount createUserAccount(UserAccount userAccount);

    /**
     * @author Fuad Hassan Jama (s233468)
     */
    Optional<UserAccount> findById(UserAccountId id);

    /**
     * @author Dilara Eda Celepli (s184262)
     */
    List<UserAccount> findAll();

    /**
     * @author Clair Norah Mutebi (s184187)
     */
    void deleteUserAccount(UserAccountId id);
}
