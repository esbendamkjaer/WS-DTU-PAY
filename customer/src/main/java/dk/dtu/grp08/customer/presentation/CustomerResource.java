package dk.dtu.grp08.customer.presentation;

import dk.dtu.grp08.customer.presentation.contracts.IAccountAPI;
import dk.dtu.grp08.customer.presentation.contracts.ICustomerResource;
import dk.dtu.grp08.customer.presentation.contracts.ITokenAPI;
import dk.dtu.grp08.customer.presentation.models.Token;
import dk.dtu.grp08.customer.presentation.models.UserAccount;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CustomerResource implements ICustomerResource {

    @RestClient
    private ITokenAPI tokenResource;

    @RestClient
    private IAccountAPI accountResource;

    public CustomerResource() {
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
    public UserAccount createCustomer(
        UserAccount userAccount
    ) {
        return this.accountResource.createUserAccount(
            userAccount
        );
    }

    @Override
    public void deleteCustomer(UUID userId) {
        this.accountResource.deleteUserAccount(
            userId
        );
    }

}
