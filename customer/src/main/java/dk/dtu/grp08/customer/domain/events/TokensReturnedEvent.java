package dk.dtu.grp08.customer.domain.events;


import dk.dtu.grp08.customer.domain.models.CorrelationId;
import dk.dtu.grp08.customer.domain.models.Token;
import lombok.Value;

import java.util.List;

@Value
public class TokensReturnedEvent {
    CorrelationId correlationId;
    List<Token> tokens;
}
