package dk.dtu.grp08.token.domain.events;

import dk.dtu.grp08.token.domain.models.CorrelationId;
import dk.dtu.grp08.token.domain.models.UserId;
import lombok.Value;

@Value
public class TokensRequestedEvent {
    CorrelationId correlationId;
    UserId userId;
    int count;
}
