package dk.dtu.grp08.account.domain.repository;

import dk.dtu.grp08.account.domain.models.UserAccount;

public interface IAccountRepository {
    UserAccount findById(String id);
}
