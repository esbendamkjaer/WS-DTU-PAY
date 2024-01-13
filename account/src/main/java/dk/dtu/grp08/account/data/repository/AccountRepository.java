package dk.dtu.grp08.account.data.repository;

import dk.dtu.grp08.account.domain.models.UserAccountId;
import dk.dtu.grp08.account.domain.models.UserAccount;
import dk.dtu.grp08.account.domain.repository.IAccountRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.val;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class AccountRepository implements IAccountRepository {
    private final Map<UserAccountId, UserAccount> userAccounts = new ConcurrentHashMap<>();

    @Override
    public UserAccount createUserAccount(UserAccount userAccount) {
        val id = userAccount.getId();
        userAccounts.put(id, userAccount);
        return userAccount;
    }

    @Override
    public void delete(UserAccount userAccount) {
        userAccounts.remove(userAccount.getId());
    }

    @Override
    public void delete(UserAccountId userAccountId) {
        userAccounts.remove(userAccountId);
    }

    @Override
    public Optional<UserAccount> getUserAccountById(UserAccountId userId) {
        return Optional.ofNullable(
            this.userAccounts.get(userId)
        );
    }
}
