package dk.dtu.grp08.account.domain.events;

import dk.dtu.grp08.account.domain.models.CorrelationId;
import dk.dtu.grp08.account.domain.models.user.UserAccountId;
import lombok.Value;

/**
 * @author Alexander Matzen (s233475)
 */
@Value
public class UserNotFoundEvent {
    CorrelationId correlationId;
    UserAccountId userId;
}
