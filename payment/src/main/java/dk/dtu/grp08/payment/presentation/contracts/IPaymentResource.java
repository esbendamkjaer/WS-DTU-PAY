package dk.dtu.grp08.payment.presentation.contracts;

import dk.dtu.grp08.payment.domain.models.payment.Payment;
import dk.dtu.grp08.payment.domain.models.PaymentRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.concurrent.CompletableFuture;

@Path("/payments")
public interface IPaymentResource {

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    CompletableFuture<Payment> createPayment(
        PaymentRequest paymentRequest
    );

}

