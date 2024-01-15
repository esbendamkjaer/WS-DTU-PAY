package dk.dtu.grp08.stubs.account;

import dk.dtu.grp08.stubs.account.models.user.BankAccountNo;
import dk.dtu.grp08.stubs.account.models.user.UserAccount;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.Optional;
import java.util.UUID;

@Path("/accounts")
public interface IAccountResource {

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    UserAccount createUserAccount(
        UserAccount userAccount
    );

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    Optional<UserAccount> getUserAccount(@PathParam("id") UUID id);

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    UserAccount[] getAllUserAccounts();

    @DELETE
    @Path("/{id}")
    void deleteUserAccount(@PathParam("id") UUID id);

}
