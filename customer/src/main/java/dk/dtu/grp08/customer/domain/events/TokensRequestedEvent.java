package dk.dtu.grp08.customer.domain.events;


import dk.dtu.grp08.customer.domain.models.CorrelationId;
import dk.dtu.grp08.customer.domain.models.UserAccountId;
import lombok.Value;

@Value
public class TokensRequestedEvent {
    /**
     *
     * @author Esben Damkjær Sørensen (s233474)
     */
    CorrelationId correlationId;
    UserAccountId userId;
    int count;
}
