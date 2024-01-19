package dk.dtu.grp08.customer.domain.events;

import dk.dtu.grp08.customer.domain.models.CorrelationId;
import dk.dtu.grp08.customer.domain.models.UserAccountId;
import lombok.Value;

@Value
public class UserNotFoundEvent {

    /**
     *
     * @author Clair Norah Mutebi (s184187)
     */
    CorrelationId correlationId;
    UserAccountId userId;
}
