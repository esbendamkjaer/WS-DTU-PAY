package dk.dtu.grp08.token.domain.events;

import dk.dtu.grp08.token.domain.models.CorrelationId;
import lombok.Value;

/**
 * @author Esben Damkjær Sørensen (s233474)
 */
@Value
public class TokenRequestFailed {
    CorrelationId correlationId;
    String cause;
}
