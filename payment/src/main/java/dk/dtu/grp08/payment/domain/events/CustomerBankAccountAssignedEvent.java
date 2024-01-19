package dk.dtu.grp08.payment.domain.events;

import dk.dtu.grp08.payment.domain.models.CorrelationId;
import dk.dtu.grp08.payment.domain.models.payment.BankAccountNo;
import dk.dtu.grp08.payment.domain.models.user.UserAccountId;
import lombok.Value;

/**
 * @author Fuad Hassan Jama (s233468)
 */
@Value
public class CustomerBankAccountAssignedEvent {
    CorrelationId correlationId;
    BankAccountNo bankAccountNo;
    UserAccountId userId;
}
