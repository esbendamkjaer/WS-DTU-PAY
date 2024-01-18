package dk.dtu.grp08.merchant.domain.services.contracts;

import dk.dtu.grp08.merchant.domain.models.Payment;
import dk.dtu.grp08.merchant.domain.models.PaymentRequest;

import java.util.concurrent.CompletableFuture;

public interface IPaymentService {
    CompletableFuture<Payment> createPayment(PaymentRequest paymentRequest);
}
