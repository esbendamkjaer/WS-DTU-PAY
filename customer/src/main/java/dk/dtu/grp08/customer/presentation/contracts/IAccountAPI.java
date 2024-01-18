package dk.dtu.grp08.customer.presentation.contracts;

import dk.dtu.grp08.customer.domain.models.UserAccount;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.Optional;
import java.util.UUID;

@Path("/accounts")
@RegisterRestClient(configKey = "ms.account")
public interface IAccountAPI {

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    UserAccount createUserAccount(
        UserAccount userAccount
    );

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    Optional<UserAccount> getUserAccount(
        @PathParam("id") UUID id
    );

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    UserAccount[] getAllUserAccounts();

    @DELETE
    @Path("/{id}")
    void deleteUserAccount(@PathParam("id") UUID id);

}
