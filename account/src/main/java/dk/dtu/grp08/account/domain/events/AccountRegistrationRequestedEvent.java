package dk.dtu.grp08.account.domain.events;


import dk.dtu.grp08.account.domain.models.CorrelationId;
import dk.dtu.grp08.account.domain.models.user.UserAccount;
import lombok.Value;

/**
 * @author Dilara
 */
@Value
public class AccountRegistrationRequestedEvent {
    CorrelationId correlationId;
    UserAccount userAccount;
}
