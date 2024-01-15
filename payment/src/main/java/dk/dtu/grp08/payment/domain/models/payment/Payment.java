package dk.dtu.grp08.payment.domain.models.payment;

import dk.dtu.grp08.payment.domain.services.IPolicy;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Payment implements IPolicy {
    private BankAccountNo debtor;
    private BankAccountNo creditor;
    private BigDecimal amount;
}
