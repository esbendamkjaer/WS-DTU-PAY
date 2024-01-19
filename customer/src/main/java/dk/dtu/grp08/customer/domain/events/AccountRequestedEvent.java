package dk.dtu.grp08.customer.domain.events;

import dk.dtu.grp08.customer.domain.models.CorrelationId;
import dk.dtu.grp08.customer.domain.models.UserAccountId;
import lombok.Value;

@Value
public class AccountRequestedEvent {
    /**
     *
     * @author Alexander
     */
    CorrelationId correlationId;
    UserAccountId userId;
}
