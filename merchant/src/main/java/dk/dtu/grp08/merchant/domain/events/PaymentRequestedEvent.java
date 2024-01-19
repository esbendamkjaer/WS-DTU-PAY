package dk.dtu.grp08.merchant.domain.events;

import dk.dtu.grp08.merchant.domain.models.CorrelationId;
import dk.dtu.grp08.merchant.domain.models.PaymentRequest;
import lombok.Value;

/**
 * @author Dilara Eda Celepli (s184262)
 */
@Value
public class PaymentRequestedEvent {
    CorrelationId correlationId;
    PaymentRequest paymentRequest;
}
