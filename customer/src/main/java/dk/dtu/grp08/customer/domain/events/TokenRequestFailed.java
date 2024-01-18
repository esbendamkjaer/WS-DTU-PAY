package dk.dtu.grp08.customer.domain.events;

import dk.dtu.grp08.customer.domain.models.CorrelationId;
import lombok.Value;

@Value
public class TokenRequestFailed {
    CorrelationId correlationId;
    String cause;
}
