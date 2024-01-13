package dk.dtu.grp08.account.domain.events;

import dk.dtu.grp08.account.domain.models.CorrelationId;
import dk.dtu.grp08.account.domain.models.user.BankAccountNo;
import lombok.Value;

@Value
public class BankAccountNoAssignedEvent {
    BankAccountNo bankAccountNo;
    CorrelationId correlationId;
}
