package dk.dtu.grp08.account.domain.events;

import dk.dtu.grp08.account.domain.models.CorrelationId;
import dk.dtu.grp08.account.domain.models.Token;
import lombok.Value;

/**
 * @author Clair Norah Mutebi (s184187)
 */
@Value
public class TokenInvalidatedEvent {
    Token token;
    CorrelationId correlationId;
}
