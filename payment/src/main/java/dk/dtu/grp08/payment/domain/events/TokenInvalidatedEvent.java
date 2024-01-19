package dk.dtu.grp08.payment.domain.events;

import dk.dtu.grp08.payment.domain.models.CorrelationId;
import dk.dtu.grp08.payment.domain.models.Token;
import lombok.Value;

/**
 * @author Dilara
 */
@Value
public class TokenInvalidatedEvent {
    CorrelationId correlationId;
    Token token;
}
