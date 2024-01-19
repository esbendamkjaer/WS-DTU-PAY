package dk.dtu.grp08.merchant.domain.events;

import dk.dtu.grp08.merchant.domain.models.CorrelationId;
import dk.dtu.grp08.merchant.domain.models.UserId;
import lombok.Value;
/**
 * @author Muhamad Hussein Nadali (s233479)
 */
@Value
public class UserNotFoundEvent {
    CorrelationId correlationId;
    UserId userId;
}
