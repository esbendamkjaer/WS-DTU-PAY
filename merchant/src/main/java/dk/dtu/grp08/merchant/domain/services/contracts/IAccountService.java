package dk.dtu.grp08.merchant.domain.services.contracts;

import dk.dtu.grp08.merchant.domain.models.UserAccount;
import dk.dtu.grp08.merchant.domain.models.UserId;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
/**
 * @author Muhamad Hussein Nadali (s233479)
 */
public interface IAccountService {
    CompletableFuture<Void> deleteUserAccount(UserId userId);

    CompletableFuture<UserAccount> createUserAccount(UserAccount userAccount);
}
