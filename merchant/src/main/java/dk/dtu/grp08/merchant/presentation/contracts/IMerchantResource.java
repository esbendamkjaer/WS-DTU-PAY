package dk.dtu.grp08.merchant.presentation.contracts;

import dk.dtu.grp08.merchant.domain.models.Payment;
import dk.dtu.grp08.merchant.domain.models.PaymentRequest;
import dk.dtu.grp08.merchant.domain.models.UserAccount;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Path("/merchants")
public interface IMerchantResource {

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    CompletableFuture<UserAccount> createMerchant(
        UserAccount userAccount
    );

    @DELETE
    @Path("/{userId}")
    CompletableFuture<Void> deleteMerchant(
        @PathParam("userId") UUID userId
    );

    @POST
    @Path("/payment")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    CompletableFuture<Payment> createPayment(
        PaymentRequest paymentRequest
    );

    @GET
    @Path("/{userId}/report")
    @Produces({MediaType.APPLICATION_JSON})
    CompletableFuture<List<Payment>> getReport(@PathParam("userId") UUID userId);




}
