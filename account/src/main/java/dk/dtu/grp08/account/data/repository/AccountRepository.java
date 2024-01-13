package dk.dtu.grp08.account.data.repository;

import dk.dtu.grp08.account.domain.models.UserAccountId;
import dk.dtu.grp08.account.domain.models.UserAccount;
import dk.dtu.grp08.account.domain.repository.IAccountRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.val;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class AccountRepository implements IAccountRepository {
    private final Map<UserAccountId, UserAccount> userAccounts = new HashMap<>();

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
    public UserAccount findById(UserAccountId id) {
        return userAccounts.get(id);
    }

    @Override
    public List<UserAccount> findAll() {
        return (List<UserAccount>) userAccounts.values();
    }
}
