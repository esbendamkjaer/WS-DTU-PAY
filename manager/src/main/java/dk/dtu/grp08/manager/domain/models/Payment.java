package dk.dtu.grp08.manager.domain.models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Payment {
    private BankAccountNo debtor;
    private BankAccountNo creditor;
    private BigDecimal amount;
}
