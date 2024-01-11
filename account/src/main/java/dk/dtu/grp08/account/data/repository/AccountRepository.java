package dk.dtu.grp08.account.data.repository;

import dk.dtu.grp08.account.domain.repository.IAccountRepository;
import jakarta.enterprise.context.ApplicationScoped;
import dk.dtu.grp08.account.domain.models.UserAccount;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class AccountRepository implements IAccountRepository {
    private final Map<String, UserAccount> userAccounts = new HashMap<>();

    @Override
    public UserAccount findById(String id) {
        return null;
    }
}
