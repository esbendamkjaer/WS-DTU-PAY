package dk.dtu.grp08.stubs.payment;

import dk.dtu.grp08.stubs.payment.models.Payment;
import dk.dtu.grp08.stubs.payment.models.PaymentRequest;
import dk.dtu.grp08.stubs.token.models.Token;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.math.BigDecimal;
import java.util.UUID;

@Path("/payments")
public interface IPaymentResource {

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    Payment createPayment(
        PaymentRequest paymentRequest
    );

}
