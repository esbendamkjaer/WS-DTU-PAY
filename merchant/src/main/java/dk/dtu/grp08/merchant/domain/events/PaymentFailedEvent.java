package dk.dtu.grp08.merchant.domain.events;

import dk.dtu.grp08.merchant.domain.models.CorrelationId;
import lombok.Value;

/**
 * @author Dilara Eda Celepli (s184262)
 */
@Value
public class PaymentFailedEvent {
    CorrelationId correlationId;
    String cause;
}
