package dk.dtu.grp08.merchant.domain.events;

import dk.dtu.grp08.merchant.domain.models.CorrelationId;
import dk.dtu.grp08.merchant.domain.models.PaymentRequest;
import lombok.Value;

@Value
public class PaymentRequestedEvent {
    CorrelationId correlationId;
    PaymentRequest paymentRequest;
}
