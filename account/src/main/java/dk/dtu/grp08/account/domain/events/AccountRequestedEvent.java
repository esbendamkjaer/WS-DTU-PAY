package dk.dtu.grp08.account.domain.events;

import dk.dtu.grp08.account.domain.models.CorrelationId;
import dk.dtu.grp08.account.domain.models.user.UserAccountId;
import lombok.Value;

/**
 * @author Clair
 */
@Value
public class AccountRequestedEvent {
    CorrelationId correlationId;
    UserAccountId userId;
}
