package dk.dtu.grp08.token.domain.events;

import dk.dtu.grp08.token.domain.models.CorrelationId;
import dk.dtu.grp08.token.domain.models.Token;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
public class PaymentRequestedEvent {
    UUID merchantID;
    Token token;
    BigDecimal amount;
    CorrelationId correlationId;
}