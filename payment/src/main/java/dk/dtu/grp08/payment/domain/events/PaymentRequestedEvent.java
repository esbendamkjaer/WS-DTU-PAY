package dk.dtu.grp08.payment.domain.events;

import dk.dtu.grp08.payment.domain.models.CorrelationId;
import dk.dtu.grp08.payment.domain.models.Token;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import messaging.Event;

import java.math.BigDecimal;
import java.util.UUID;

@Value
public class PaymentRequestedEvent {
    UUID merchantID;
    Token token;
    BigDecimal amount;
    CorrelationId correlationId;
}
