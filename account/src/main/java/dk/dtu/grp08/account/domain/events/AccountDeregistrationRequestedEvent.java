package dk.dtu.grp08.account.domain.events;

import dk.dtu.grp08.account.domain.models.CorrelationId;
import dk.dtu.grp08.account.domain.models.user.UserAccountId;
import lombok.Value;

@Value
public class AccountDeregistrationRequestedEvent {
    CorrelationId correlationId;
    UserAccountId userId;
}
