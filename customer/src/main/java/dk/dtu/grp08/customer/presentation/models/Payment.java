package dk.dtu.grp08.customer.presentation.models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Payment {
    private BankAccountNo debtor;
    private BankAccountNo creditor;
    private BigDecimal amount;
}
