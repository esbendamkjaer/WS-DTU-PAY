package dk.dtu.grp08.payment.domain.models.payment;

import dk.dtu.grp08.payment.domain.services.IPolicy;
import lombok.Value;

@Value
public class BankAccountNo implements IPolicy {
    String bankAccountNo;
}
