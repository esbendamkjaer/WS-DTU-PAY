package dk.dtu.grp08.account.domain.events;

import dk.dtu.grp08.account.domain.models.CorrelationId;
import dk.dtu.grp08.account.domain.models.user.UserAccountId;
import lombok.Value;

/**
 * @author Esben Damkjær Sørensen (s233474)
 */
@Value
public class AccountDeregistrationRequestedEvent {
    CorrelationId correlationId;
    UserAccountId userId;
}
