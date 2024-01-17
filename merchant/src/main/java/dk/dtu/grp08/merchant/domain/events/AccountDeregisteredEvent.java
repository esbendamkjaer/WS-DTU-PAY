package dk.dtu.grp08.merchant.domain.events;

import dk.dtu.grp08.merchant.domain.models.CorrelationId;
import lombok.Value;

@Value
public class AccountDeregisteredEvent {
    CorrelationId correlationId;
}
