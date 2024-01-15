package dk.dtu.grp08.account.presentation.contracts;

import dk.dtu.grp08.account.domain.models.user.BankAccountNo;
import dk.dtu.grp08.account.domain.models.user.UserAccount;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.Optional;
import java.util.UUID;

@Path("/accounts")
public interface IAccountResource {

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Operation(summary = "Create a new user account", description = "Create a new user account")
    @APIResponse(responseCode = "200", description = "User account created successfully")
    @APIResponse(responseCode = "400", description = "Bad Request - Error message is dynamically generated based on the specific request issue.")
    @APIResponse(responseCode = "500", description = "Internal server error")
    UserAccount createUserAccount(
        UserAccount userAccount
    );

    @Produces({MediaType.APPLICATION_JSON})
    @GET
    @Path("/{id}")
    @Operation(summary = "Get a user account", description = "Get a user account")
    @APIResponse(responseCode = "200", description = "User account retrieved successfully")
    @APIResponse(responseCode = "404", description = "User account not found")
    @APIResponse(responseCode = "500", description = "Internal server error")
    Optional<UserAccount> getUserAccount(@PathParam("id") UUID id);

    @Produces({MediaType.APPLICATION_JSON})
    @GET
    @Operation(summary = "Get all user accounts", description = "Get all user accounts")
    @APIResponse(responseCode = "200", description = "User accounts retrieved successfully")
    @APIResponse(responseCode = "500", description = "Internal server error")
    UserAccount[] getAllUserAccounts();

    @DELETE
    @Path("/{id}")
    void deleteUserAccount(@PathParam("id") UUID id);

}
