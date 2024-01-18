package dk.dtu.grp08.payment.domain.events;

import dk.dtu.grp08.payment.domain.models.CorrelationId;
import lombok.Value;

@Value
public class PaymentFailedEvent {
    CorrelationId correlationId;
    String cause;
}
