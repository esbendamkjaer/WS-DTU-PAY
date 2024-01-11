package dk.dtu.grp08.payment.presentation;

import dk.dtu.grp08.payment.domain.services.PaymentService;
import dk.dtu.grp08.payment.presentation.contracts.IPaymentResource;
import jakarta.inject.Inject;

public class PaymentResource implements IPaymentResource {
    @Inject
    PaymentService paymentService;

    

}
