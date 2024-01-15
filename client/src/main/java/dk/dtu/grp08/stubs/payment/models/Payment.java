package dk.dtu.grp08.stubs.payment.models;

import dk.dtu.grp08.stubs.account.models.user.BankAccountNo;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Payment {
    private BankAccountNo debtor;
    private BankAccountNo creditor;
    private BigDecimal amount;
}
