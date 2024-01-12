package dk.dtu.grp08.account.domain.events;

import dk.dtu.grp08.account.domain.models.CorrelationId;
import dk.dtu.grp08.account.domain.models.Token;
import dk.dtu.grp08.account.domain.models.useraccount.UserId;
import lombok.Value;

@Value
public class TokenValidatedEvent {
    CorrelationId correlationId;
    Token token;
    UserId userId;
}
