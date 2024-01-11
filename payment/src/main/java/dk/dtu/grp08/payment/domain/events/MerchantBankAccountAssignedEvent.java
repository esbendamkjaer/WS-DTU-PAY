package dk.dtu.grp08.payment.domain.events;

import dk.dtu.grp08.payment.domain.models.CorrelationId;
import dk.dtu.grp08.payment.domain.models.payment.BankAccountNo;
import lombok.Value;

@Value
public class MerchantBankAccountAssignedEvent {
    CorrelationId correlationId;
    BankAccountNo bankAccountNo;
}