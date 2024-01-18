package dk.dtu.grp08.customer.domain.events;

import dk.dtu.grp08.customer.domain.models.CorrelationId;
import dk.dtu.grp08.customer.domain.models.UserAccountId;
import lombok.Value;

@Value
public class UserNotFoundEvent {
    CorrelationId correlationId;
    UserAccountId userId;
}