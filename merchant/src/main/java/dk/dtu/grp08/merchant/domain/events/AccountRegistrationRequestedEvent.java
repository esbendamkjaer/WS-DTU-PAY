package dk.dtu.grp08.merchant.domain.events;

import dk.dtu.grp08.merchant.domain.models.CorrelationId;
import dk.dtu.grp08.merchant.domain.models.UserAccount;
import lombok.Value;

/**
 * @author Esben Damkjær Sørensen (s233474)
 */
@Value
public class AccountRegistrationRequestedEvent {
    CorrelationId correlationId;
    UserAccount userAccount;
}
