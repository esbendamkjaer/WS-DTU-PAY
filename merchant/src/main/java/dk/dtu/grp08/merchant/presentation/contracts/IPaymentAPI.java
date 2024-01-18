package dk.dtu.grp08.merchant.presentation.contracts;

import dk.dtu.grp08.merchant.domain.models.Payment;
import dk.dtu.grp08.merchant.domain.models.PaymentRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/payments")
@RegisterRestClient(baseUri = "http://localhost:8083")
public interface IPaymentAPI {

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    Payment createPayment(
        PaymentRequest paymentRequest
    );

}
