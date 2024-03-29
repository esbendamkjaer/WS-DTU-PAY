package dk.dtu.grp08.account.domain.events;

import dk.dtu.grp08.account.domain.models.CorrelationId;
import dk.dtu.grp08.account.domain.models.Token;
import dk.dtu.grp08.account.domain.models.user.UserAccountId;
import lombok.Value;

/**
 * @author Muhamad Hussein Nadali (s233479)
 */
@Value
public class TokenValidatedEvent {
    CorrelationId correlationId;
    Token token;
    UserAccountId userAccountId;
}
