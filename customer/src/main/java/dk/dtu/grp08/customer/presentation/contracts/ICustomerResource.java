package dk.dtu.grp08.customer.presentation.contracts;

import dk.dtu.grp08.customer.presentation.models.Token;
import dk.dtu.grp08.customer.presentation.models.UserAccount;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;

@Path("/customers")
public interface ICustomerResource {

    @GET
    @Path("/{userId}/tokens")
    @Produces({MediaType.APPLICATION_JSON})
    List<Token> getTokens(
        @PathParam("userId") UUID userId,
        @QueryParam("count") int count
    );

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    UserAccount createCustomer(
        UserAccount userAccount
    );

    @DELETE
    @Path("/{userId}")
    void deleteCustomer(
        @PathParam("userId") UUID userId
    );

    @GET
    @Path("/{userId}")
    @Produces({MediaType.APPLICATION_JSON})
    UserAccount getCustomer(@PathParam("userId") UUID userId);

}
