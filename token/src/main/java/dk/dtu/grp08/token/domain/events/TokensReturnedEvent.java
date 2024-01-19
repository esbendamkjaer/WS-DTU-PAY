package dk.dtu.grp08.token.domain.events;

import dk.dtu.grp08.token.domain.models.CorrelationId;
import dk.dtu.grp08.token.domain.models.Token;
import lombok.Value;

import java.util.List;

/**
 * @author Muhamad Hussein Nadali (s233479)
 */
@Value
public class TokensReturnedEvent {
    CorrelationId correlationId;
    List<Token> tokens;
}
