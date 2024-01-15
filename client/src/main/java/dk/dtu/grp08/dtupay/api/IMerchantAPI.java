package dk.dtu.grp08.dtupay.api;

import dk.dtu.grp08.dtupay.models.Payment;
import dk.dtu.grp08.dtupay.models.PaymentRequest;
import dk.dtu.grp08.dtupay.models.UserAccount;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

@Path("/merchants")
public interface IMerchantAPI {

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

}
