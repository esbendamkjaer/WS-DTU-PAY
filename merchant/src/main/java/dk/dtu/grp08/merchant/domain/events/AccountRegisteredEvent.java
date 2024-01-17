package dk.dtu.grp08.merchant.domain.events;

import dk.dtu.grp08.merchant.domain.models.CorrelationId;
import dk.dtu.grp08.merchant.domain.models.UserAccount;
import lombok.Value;

@Value
public class AccountRegisteredEvent {
    CorrelationId correlationId;
    UserAccount userAccount;
}
