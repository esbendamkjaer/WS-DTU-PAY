package dk.dtu.grp08.account.domain.events;

import dk.dtu.grp08.account.domain.models.CorrelationId;
import lombok.Value;

/**
 * @author Muhamad Hussein Nadali (s233479)
 */
@Value
public class AccountDeregisteredEvent {
    CorrelationId correlationId;
}
