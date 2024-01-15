package dk.dtu.grp08.payment.presentation;

import dk.dtu.grp08.payment.domain.models.payment.Payment;
import dk.dtu.grp08.payment.domain.services.IPaymentService;
import dk.dtu.grp08.payment.presentation.contracts.IPaymentResource;
import dk.dtu.grp08.payment.domain.models.PaymentRequest;
import jakarta.ws.rs.Path;

@Path("/payments")
public class PaymentResource implements IPaymentResource {

    private final IPaymentService paymentService;

    public PaymentResource(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public Payment createPayment(PaymentRequest paymentRequest) {
        return paymentService.requestPayment(
            paymentRequest.getMerchantId(),
            paymentRequest.getToken(),
            paymentRequest.getAmount()
        );
    }
}
