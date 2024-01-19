package dk.dtu.grp08.account.domain.events;

import dk.dtu.grp08.account.domain.models.CorrelationId;
import dk.dtu.grp08.account.domain.models.user.BankAccountNo;
import dk.dtu.grp08.account.domain.models.user.UserAccountId;
import lombok.Value;

/**
 * @author Alexander Matzen (s233475)
 */
@Value
public class BankAccountNoAssignedEvent {
    BankAccountNo bankAccountNo;
    CorrelationId correlationId;
    UserAccountId userId;
}
