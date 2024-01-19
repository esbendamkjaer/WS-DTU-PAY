package dk.dtu.grp08.customer.domain.events;

import dk.dtu.grp08.customer.domain.models.CorrelationId;
import dk.dtu.grp08.customer.domain.models.UserAccount;
import lombok.Value;

@Value
public class AccountRegistrationRequestedEvent {

    /**
     *
     * @author Alexander
     */
    CorrelationId correlationId;
    UserAccount userAccount;
}
