package dk.dtu.grp08.merchant.domain.events;

import dk.dtu.grp08.merchant.domain.models.CorrelationId;
import dk.dtu.grp08.merchant.domain.models.UserAccount;
import lombok.Value;

@Value
public class AccountRegistrationRequestedEvent {
    CorrelationId correlationId;
    UserAccount userAccount;
}
