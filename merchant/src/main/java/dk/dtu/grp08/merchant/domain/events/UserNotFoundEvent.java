package dk.dtu.grp08.merchant.domain.events;

import dk.dtu.grp08.merchant.domain.models.CorrelationId;
import dk.dtu.grp08.merchant.domain.models.UserId;
import lombok.Value;

@Value
public class UserNotFoundEvent {
    CorrelationId correlationId;
    UserId userId;
}
