package dk.dtu.grp08.account.domain.events;


import dk.dtu.grp08.account.domain.models.CorrelationId;
import dk.dtu.grp08.account.domain.models.user.UserAccount;
import lombok.Value;

/**
 * @author Muhamad
 */
@Value
public class AccountReturnedEvent {
    CorrelationId correlationId;
    UserAccount userAccount;
}
