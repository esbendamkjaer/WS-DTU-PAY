package dk.dtu.grp08.payment.domain.services;

import dk.dtu.grp08.payment.domain.models.Token;
import dk.dtu.grp08.payment.domain.models.payment.Payment;
import dk.dtu.grp08.payment.domain.util.policy.Policy;

import java.math.BigDecimal;
import java.util.UUID;

public interface IPaymentService {

    Payment makePayment(Payment payment, UUID merchantID, Token token);

    Policy<Payment> initiatePayment(
        final UUID merchantID,
        final Token token,
        final BigDecimal amount
    );

}
