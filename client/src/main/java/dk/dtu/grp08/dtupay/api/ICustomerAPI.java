package dk.dtu.grp08.dtupay.api;

import dk.dtu.grp08.dtupay.models.Payment;
import dk.dtu.grp08.dtupay.models.Token;
import dk.dtu.grp08.dtupay.models.UserAccount;
import dk.dtu.grp08.dtupay.models.UserId;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Path("/customers")
public interface ICustomerAPI {

    /**
     * @author Dilara
     */
    @POST
    @Path("/{userId}/tokens")
    @Produces({MediaType.APPLICATION_JSON})
    List<Token> getTokens(
        @PathParam("userId") UUID userId,
        @QueryParam("count") int count
    );

    /**
     * @author Clair
     */
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    UserAccount createCustomer(
        UserAccount userAccount
    );

    /**
     * @author Clair
     */
    @DELETE
    @Path("/{userId}")
    void deleteCustomer(
        @PathParam("userId") UUID userId
    );

    /**
     * @author Muhamad
     */
    @GET
    @Path("/{userId}")
    @Produces({MediaType.APPLICATION_JSON})
    UserAccount getCustomer(
        @PathParam("userId") UUID userId
    );

    /**
     * @author Alexander
     */
    @GET
    @Path("/{userId}/report")
    @Produces({MediaType.APPLICATION_JSON})
    List<Payment> getReport(@PathParam("userId") UUID userId);

}
