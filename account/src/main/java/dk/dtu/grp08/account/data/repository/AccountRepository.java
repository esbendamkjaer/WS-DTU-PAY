package dk.dtu.grp08.account.data.repository;

import dk.dtu.grp08.account.domain.models.user.UserAccountId;
import dk.dtu.grp08.account.domain.models.user.UserAccount;
import dk.dtu.grp08.account.domain.repository.IAccountRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.val;

import java.util.List;
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
    public void delete(UserAccountId userAccountId) {
        if (!userAccounts.containsKey(userAccountId)) {
            throw new NotFoundException(
                Response.status(404).entity("user account with id " + userAccountId + " is unknown").build()
            );
        }
        userAccounts.remove(userAccountId);
    }

    @Override
    public Optional<UserAccount> findById(UserAccountId id) {
        return Optional.ofNullable(this.userAccounts.get(id));
    }

    @Override
    public List<UserAccount> findAll() {
        return (List<UserAccount>) this.userAccounts.values();
    }

}
