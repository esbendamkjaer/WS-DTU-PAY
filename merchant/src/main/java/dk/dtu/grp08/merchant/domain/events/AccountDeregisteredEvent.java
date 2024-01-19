package dk.dtu.grp08.merchant.domain.events;

import dk.dtu.grp08.merchant.domain.models.CorrelationId;
import lombok.Value;

/**
 * @author Alexander Matzen (s233475)
 */

@Value
public class AccountDeregisteredEvent {
    CorrelationId correlationId;
}
