package dk.dtu.grp08.customer.presentation;

import dk.dtu.grp08.customer.presentation.contracts.IAccountAPI;
import dk.dtu.grp08.customer.presentation.contracts.ICustomerResource;
import dk.dtu.grp08.customer.presentation.contracts.ITokenAPI;
import dk.dtu.grp08.customer.presentation.models.Token;
import dk.dtu.grp08.customer.presentation.models.UserAccount;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.client.exception.ResteasyBadRequestException;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CustomerResource implements ICustomerResource {

    private final ITokenAPI tokenResource;
    private final IAccountAPI accountResource;

    public CustomerResource() {
        tokenResource = Stub.get(ITokenAPI.class, "http://localhost:8082");
        accountResource = Stub.get(IAccountAPI.class, "http://localhost:8081");
    }

    @Override
    public List<Token> getTokens(
        UUID userId,
        int count
    ) {
        try {
            return tokenResource.getTokens(
                count,
                userId
            );
        } catch (ClientErrorException e) {
            System.out.println("The entity: " + e.getResponse().readEntity(String.class));
            throw new WebApplicationException(
                Response
                    .status(e.getResponse().getStatus())
                    .entity(e.getResponse().getEntity())
                    .build()
            );
        }
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
