package dk.dtu.grp08.customer.presentation.contracts;

import dk.dtu.grp08.customer.domain.models.Token;
import dk.dtu.grp08.customer.domain.models.UserAccount;
import dk.dtu.grp08.customer.domain.models.Payment;
import dk.dtu.grp08.customer.domain.models.Token;
import dk.dtu.grp08.customer.domain.models.UserAccount;
import dk.dtu.grp08.customer.domain.models.Payment;
import dk.dtu.grp08.customer.domain.models.Token;
import dk.dtu.grp08.customer.domain.models.UserAccount;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletableFuture;

@Path("/customers")
public interface ICustomerResource {

    @POST
    @Path("/{userId}/tokens")
    @Produces({MediaType.APPLICATION_JSON})
    CompletableFuture<List<Token>> getTokens(
        @PathParam("userId") UUID userId,
        @QueryParam("count") int count
    );

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    CompletableFuture<UserAccount> createCustomer(
        UserAccount userAccount
    );

    @DELETE
    @Path("/{userId}")
    CompletableFuture<Void> deleteCustomer(
        @PathParam("userId") UUID userId
    );

    @GET
    @Path("/{userId}")
    @Produces({MediaType.APPLICATION_JSON})
    CompletableFuture<UserAccount> getCustomer(@PathParam("userId") UUID userId);


    @GET
    @Path("/{userId}/report")
    @Produces({MediaType.APPLICATION_JSON})
    CompletableFuture<List<Payment>> getReport(@PathParam("userId") UUID userId);


}
