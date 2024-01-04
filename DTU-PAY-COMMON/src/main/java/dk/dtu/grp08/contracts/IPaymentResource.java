package dk.dtu.grp08.contracts;

import dk.dtu.grp08.models.Payment;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("/payments")
public interface IPaymentResource {


    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    boolean createPayment(Payment customer);


}
