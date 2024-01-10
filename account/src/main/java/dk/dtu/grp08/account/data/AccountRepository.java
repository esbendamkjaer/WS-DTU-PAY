package dk.dtu.grp08.account.data;

import jakarta.enterprise.context.ApplicationScoped;
import dk.dtu.grp08.account.domain.models.UserAccount;

import java.util.HashMap;
import java.util.UUID;

@ApplicationScoped
public class AccountRepository extends HashMap<UUID, UserAccount> {

}
