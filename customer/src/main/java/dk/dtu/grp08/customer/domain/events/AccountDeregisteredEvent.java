package dk.dtu.grp08.customer.domain.events;

import dk.dtu.grp08.customer.domain.models.CorrelationId;
import lombok.Value;

@Value
public class AccountDeregisteredEvent {

    /**
     *
     * @author Clair Norah Mutebi (s184187)
     */
    CorrelationId correlationId;
}
