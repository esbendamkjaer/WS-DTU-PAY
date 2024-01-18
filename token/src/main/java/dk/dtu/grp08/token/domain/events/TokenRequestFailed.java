package dk.dtu.grp08.token.domain.events;

import dk.dtu.grp08.token.domain.models.CorrelationId;
import lombok.Value;

@Value
public class TokenRequestFailed {
    CorrelationId correlationId;
    String cause;
}
