package dk.dtu.grp08.merchant.domain.events;

import dk.dtu.grp08.merchant.domain.models.CorrelationId;
import lombok.Value;

@Value
public class PaymentFailedEvent {
    CorrelationId correlationId;
    String cause;
}
