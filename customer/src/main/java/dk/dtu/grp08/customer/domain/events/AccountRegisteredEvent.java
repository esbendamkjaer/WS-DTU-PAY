package dk.dtu.grp08.customer.domain.events;


import dk.dtu.grp08.customer.domain.models.CorrelationId;
import dk.dtu.grp08.customer.domain.models.UserAccount;
import lombok.Value;

@Value
public class AccountRegisteredEvent {
    CorrelationId correlationId;
    UserAccount userAccount;
}
