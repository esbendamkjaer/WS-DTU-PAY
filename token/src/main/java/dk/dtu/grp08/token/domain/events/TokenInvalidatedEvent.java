package dk.dtu.grp08.token.domain.events;

import dk.dtu.grp08.token.domain.models.Token;
import lombok.Value;

@Value
public class TokenInvalidatedEvent {
    Token token;
}
