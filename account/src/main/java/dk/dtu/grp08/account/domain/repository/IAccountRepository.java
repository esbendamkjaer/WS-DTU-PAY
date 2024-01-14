package dk.dtu.grp08.account.domain.repository;

import dk.dtu.grp08.account.domain.models.user.UserAccount;
import dk.dtu.grp08.account.domain.models.user.UserAccountId;

import java.util.List;
import java.util.Optional;

public interface IAccountRepository {

    UserAccount createUserAccount(UserAccount userAccount);

    void delete(UserAccountId userAccountId);

    Optional<UserAccount> findById(UserAccountId id);

    List<UserAccount> findAll();

}
