package dk.dtu.grp08.merchant.presentation.contracts;

import dk.dtu.grp08.merchant.presentation.models.UserAccount;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.Optional;
import java.util.UUID;

@Path("/accounts")
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
