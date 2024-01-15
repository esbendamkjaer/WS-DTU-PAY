package dk.dtu.grp08.customer.presentation;

import dk.dtu.grp08.customer.presentation.contracts.IAccountResource;
import dk.dtu.grp08.customer.presentation.contracts.ICustomerResource;
import dk.dtu.grp08.customer.presentation.contracts.ITokenResource;
import dk.dtu.grp08.customer.presentation.models.Token;
import dk.dtu.grp08.customer.presentation.models.UserAccount;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CustomerResource implements ICustomerResource {

    private final ITokenResource tokenResource;
    private final IAccountResource accountResource;

    public CustomerResource() {
        tokenResource = Stub.get(ITokenResource.class, "http://localhost:8082");
        accountResource = Stub.get(IAccountResource.class, "http://localhost:8081");
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
