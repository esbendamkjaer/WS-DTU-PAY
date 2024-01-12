package dk.dtu.grp08.token.domain.events;

import dk.dtu.grp08.token.domain.models.Token;
import dk.dtu.grp08.token.domain.models.UserId;
import lombok.Value;

@Value
public class TokenValidatedEvent {
    Token token;
    UserId userId;
}
