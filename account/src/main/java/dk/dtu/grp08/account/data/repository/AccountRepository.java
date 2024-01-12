package dk.dtu.grp08.account.data.repository;

import dk.dtu.grp08.account.domain.models.useraccount.UserAccount;
import dk.dtu.grp08.account.domain.models.useraccount.UserId;
import dk.dtu.grp08.account.domain.repository.IAccountRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class AccountRepository implements IAccountRepository {

    private final Map<UserId, UserAccount> userAccounts = new ConcurrentHashMap<>();

    @Override
    public Optional<UserAccount> getUserAccountById(UserId userId) {
        return Optional.ofNullable(
            this.userAccounts.get(userId)
        );
    }
}
