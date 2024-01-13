package dk.dtu.grp08.account.domain.repository;

import dk.dtu.grp08.account.domain.models.UserAccount;
import dk.dtu.grp08.account.domain.models.UserAccountId;

import java.util.List;

public interface IAccountRepository {

    UserAccount createUserAccount(UserAccount userAccount);

    void delete(UserAccount userAccount);

    void delete(UserAccountId userAccountId);

    UserAccount findById(UserAccountId id);

    List<UserAccount> findAll();

}
