package dk.dtu.grp08.customer.presentation;

import dk.dtu.grp08.customer.domain.models.UserAccountId;
import dk.dtu.grp08.customer.domain.services.contracts.IAccountService;
import dk.dtu.grp08.customer.domain.services.contracts.ITokenService;
import dk.dtu.grp08.customer.presentation.contracts.ICustomerResource;
import dk.dtu.grp08.customer.domain.models.Token;
import dk.dtu.grp08.customer.domain.models.UserAccount;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class CustomerResource implements ICustomerResource {


    private final ITokenService tokenService;

    private final IAccountService accountService;

    public CustomerResource(
        IAccountService accountService,
        ITokenService tokenService
    ) {
        this.accountService = accountService;
        this.tokenService = tokenService;
    }

    @Override
    public CompletableFuture<List<Token>> getTokens(
        UUID userId,
        int count
    ) {
        return tokenService.getTokens(
            count,
            new UserAccountId(userId)
        );
    }

    @Override
    public CompletableFuture<UserAccount> createCustomer(
        UserAccount userAccount
    ) {
        return this.accountService.createUserAccount(
            userAccount
        );
    }

    @Override
    public CompletableFuture<Void> deleteCustomer(UUID userId) {
        return this.accountService.deleteUserAccount(
            new UserAccountId(userId)
        );
    }

    @Override
    public CompletableFuture<UserAccount> getCustomer(UUID userId) {
        return this.accountService.getUserAccount(
            new UserAccountId(userId)
        );
    }


}
