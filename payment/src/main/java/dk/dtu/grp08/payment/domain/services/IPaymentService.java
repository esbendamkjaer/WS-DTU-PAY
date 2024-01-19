package dk.dtu.grp08.payment.domain.services;

import dk.dtu.grp08.payment.domain.events.PaymentRequestedEvent;
import dk.dtu.grp08.payment.domain.events.PaymentTransferredEvent;
import dk.dtu.grp08.payment.domain.models.Token;
import dk.dtu.grp08.payment.domain.models.payment.Payment;
import dk.dtu.grp08.payment.domain.util.policy.Policy;

import java.math.BigDecimal;
import java.util.UUID;

public interface IPaymentService {

    /**
     * @author Esben Damkjær Sørensen (s233474)
     */
    Payment makePayment(Payment payment, UUID merchantID, Token token);

    /**
     * @author Dilara Eda Celepli (s184262)
     */
    Policy<PaymentTransferredEvent> initiatePayment(
        PaymentRequestedEvent paymentRequestedEvent
    );

}
