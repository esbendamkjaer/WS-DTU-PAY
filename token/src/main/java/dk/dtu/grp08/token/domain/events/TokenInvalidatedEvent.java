package dk.dtu.grp08.token.domain.events;

import dk.dtu.grp08.token.domain.models.CorrelationId;
import dk.dtu.grp08.token.domain.models.Token;
import lombok.Value;

/**
 * @author Fuad Hassan Jama (s233468)
 */
@Value
public class TokenInvalidatedEvent {
    CorrelationId correlationId;
    Token token;
}
