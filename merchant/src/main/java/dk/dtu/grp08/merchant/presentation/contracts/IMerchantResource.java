package dk.dtu.grp08.merchant.presentation.contracts;

import dk.dtu.grp08.merchant.presentation.models.Payment;
import dk.dtu.grp08.merchant.presentation.models.PaymentRequest;
import dk.dtu.grp08.merchant.presentation.models.UserAccount;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

@Path("/merchants")
public interface IMerchantResource {

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    UserAccount createMerchant(
        UserAccount userAccount
    );

    @DELETE
    @Path("/{userId}")
    void deleteMerchant(
        @PathParam("userId") UUID userId
    );

    @POST
    @Path("/payment")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    Payment createPayment(
        PaymentRequest paymentRequest
    );

    @POST
    @Path("/report")
    @Consumes({MediaType.APPLICATION_JSON})
    void getReport(UUID id);

}
