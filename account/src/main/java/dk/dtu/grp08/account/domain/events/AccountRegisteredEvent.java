package dk.dtu.grp08.account.domain.events;

import dk.dtu.grp08.account.domain.models.CorrelationId;
import dk.dtu.grp08.account.domain.models.user.UserAccount;
import lombok.Value;

@Value
public class AccountRegisteredEvent {
    CorrelationId correlationId;
    UserAccount userAccount;
}
