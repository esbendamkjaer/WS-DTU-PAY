package dk.dtu.grp08.dtupay.api;

import dk.dtu.grp08.dtupay.models.Payment;
import dk.dtu.grp08.dtupay.models.PaymentRequest;
import dk.dtu.grp08.dtupay.models.UserAccount;
import dk.dtu.grp08.dtupay.models.UserId;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Path("/merchants")
public interface IMerchantAPI {

    /**
     * @author Clair
     */
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    UserAccount createMerchant(
        UserAccount userAccount
    );

    /**
     * @author Clair
     */
    @DELETE
    @Path("/{userId}")
    void deleteMerchant(
        @PathParam("userId") UUID userId
    );

    /**
     * @author Dilara
     */
    @POST
    @Path("/payment")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    Payment createPayment(
        PaymentRequest paymentRequest
    );

    /**
     * @author Alexander
     */
    @GET
    @Path("/{userId}/report")
    @Produces({MediaType.APPLICATION_JSON})
    List<Payment> getReport(@PathParam("userId") UUID userId);

}
