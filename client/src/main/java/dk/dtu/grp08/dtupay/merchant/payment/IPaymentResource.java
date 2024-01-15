package dk.dtu.grp08.dtupay.merchant.payment;

import dk.dtu.grp08.dtupay.models.Payment;
import dk.dtu.grp08.dtupay.models.PaymentRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/payments")
public interface IPaymentResource {

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    Payment createPayment(
        PaymentRequest paymentRequest
    );

}
