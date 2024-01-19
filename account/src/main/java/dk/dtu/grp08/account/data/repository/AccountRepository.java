package dk.dtu.grp08.account.data.repository;

import dk.dtu.grp08.account.domain.exceptions.NoSuchUserAccountException;
import dk.dtu.grp08.account.domain.models.user.UserAccountId;
import dk.dtu.grp08.account.domain.models.user.UserAccount;
import dk.dtu.grp08.account.domain.repository.IAccountRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.val;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class AccountRepository implements IAccountRepository {
    private final Map<UserAccountId, UserAccount> userAccounts = new ConcurrentHashMap<>();

    /**
     * @author Esben Damkjær Sørensen (s233474)
     */
    @Override
    public UserAccount createUserAccount(UserAccount userAccount) {
        val id = userAccount.getId();
        userAccounts.put(id, userAccount);
        return userAccount;
    }

    /**
     * @author Fuad Hassan Jama (s233468)
     */
    @Override
    public Optional<UserAccount> findById(UserAccountId id) {
        return Optional.ofNullable(this.userAccounts.get(id));
    }

    /**
     * @author Dilara Eda Celepli (s184262)
     */
    @Override
    public List<UserAccount> findAll() {
        return List.copyOf(userAccounts.values());
    }

    /**
     * @author Clair Norah Mutebi (s184187)
     */
    @Override
    public void deleteUserAccount(UserAccountId id) {
        userAccounts.remove(id);
    }

}
