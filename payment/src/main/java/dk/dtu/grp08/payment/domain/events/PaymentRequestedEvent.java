package dk.dtu.grp08.payment.domain.events;

import dk.dtu.grp08.payment.domain.models.CorrelationId;
import dk.dtu.grp08.payment.domain.models.PaymentRequest;
import lombok.Value;

/**
 * @author Esben
 */
@Value
public class PaymentRequestedEvent {
    CorrelationId correlationId;
    PaymentRequest paymentRequest;
}
