package dk.dtu.grp08.payment.domain.events;


import dk.dtu.grp08.payment.domain.models.CorrelationId;
import dk.dtu.grp08.payment.domain.models.payment.Payment;
import lombok.Value;

@Value
public class PaymentTransferredEvent {
    CorrelationId correlationId;
    Payment payment;
}
