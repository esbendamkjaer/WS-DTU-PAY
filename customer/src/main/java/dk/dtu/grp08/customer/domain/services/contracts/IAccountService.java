package dk.dtu.grp08.customer.domain.services.contracts;

import dk.dtu.grp08.customer.domain.models.UserAccount;
import dk.dtu.grp08.customer.domain.models.UserAccountId;

import java.util.concurrent.CompletableFuture;

public interface IAccountService {

    /**
     *
     * @author Alexander Matzen (s233475)
     */
    CompletableFuture<Void> deleteUserAccount(UserAccountId userId);

    CompletableFuture<UserAccount> createUserAccount(UserAccount userAccount);

    CompletableFuture<UserAccount> getUserAccount(UserAccountId userAccountId);
}
