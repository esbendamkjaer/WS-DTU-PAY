package dk.dtu.grp08.account.domain.events;

import dk.dtu.grp08.account.domain.models.CorrelationId;
import lombok.Value;

/**
 * @author Fuad
 */
@Value
public class NoSuchUserAccountEvent {
    CorrelationId correlationId;
}
