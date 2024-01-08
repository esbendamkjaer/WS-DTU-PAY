package dk.dtu.grp08.dtupay.contracts;

import dk.dtu.grp08.dtupay.models.Payment;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;


@Path("/payments")
public interface IPaymentResource {


    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    boolean createPayment(Payment customer);


    @GET
    @Produces({MediaType.APPLICATION_JSON})

    List<Payment> listPayments();


}
