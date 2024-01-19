package dk.dtu.grp08.account.domain.events;

import dk.dtu.grp08.account.domain.models.CorrelationId;
import lombok.Value;

/**
 * @author Fuad Hassan Jama (s233468)
 */
@Value
public class NoSuchUserAccountEvent {
    CorrelationId correlationId;
}
