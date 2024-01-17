package dk.dtu.grp08.customer.presentation;

import dk.dtu.grp08.customer.domain.models.UserAccountId;
import dk.dtu.grp08.customer.domain.services.contracts.IAccountService;
import dk.dtu.grp08.customer.presentation.contracts.ICustomerResource;
import dk.dtu.grp08.customer.presentation.contracts.ITokenAPI;
import dk.dtu.grp08.customer.domain.models.Token;
import dk.dtu.grp08.customer.domain.models.UserAccount;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class CustomerResource implements ICustomerResource {

    @RestClient
    private ITokenAPI tokenResource;

    private final IAccountService accountService;

    public CustomerResource(
        IAccountService accountService
    ) {
        this.accountService = accountService;
    }

    @Override
    public List<Token> getTokens(
        UUID userId,
        int count
    ) {
        return tokenResource.getTokens(
            count,
            userId
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
