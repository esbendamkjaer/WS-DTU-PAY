package dk.dtu.grp08.account.domain.repository;

import dk.dtu.grp08.account.domain.models.user.UserAccount;
import dk.dtu.grp08.account.domain.models.user.UserAccountId;

import java.util.List;
import java.util.Optional;

public interface IAccountRepository {

    /**
     * @author Esben
     */
    UserAccount createUserAccount(UserAccount userAccount);

    /**
     * @author Fuad
     */
    Optional<UserAccount> findById(UserAccountId id);

    /**
     * @author Dilara
     */
    List<UserAccount> findAll();

    /**
     * @author Clair
     */
    void deleteUserAccount(UserAccountId id);
}
