package dk.dtu.grp08.account.domain.repository;

import dk.dtu.grp08.account.domain.models.useraccount.UserAccount;
import dk.dtu.grp08.account.domain.models.useraccount.UserId;

import java.util.Optional;

public interface IAccountRepository {

    public Optional<UserAccount> getUserAccountById(UserId userId);
}
