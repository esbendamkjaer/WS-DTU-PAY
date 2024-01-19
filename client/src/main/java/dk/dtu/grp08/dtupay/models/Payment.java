package dk.dtu.grp08.dtupay.models;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Muhamad
 */
@Data
public class Payment {
    private BankAccountNo debtor;
    private BankAccountNo creditor;
    private BigDecimal amount;
}
