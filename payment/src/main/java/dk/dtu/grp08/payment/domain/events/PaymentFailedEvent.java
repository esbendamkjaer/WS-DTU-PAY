package dk.dtu.grp08.payment.domain.events;

import dk.dtu.grp08.payment.domain.models.CorrelationId;
import lombok.Value;

/**
 * @author Muhamad Hussein Nadali (s233479)
 */
@Value
public class PaymentFailedEvent {
    CorrelationId correlationId;
    String cause;
}
