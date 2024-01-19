package dk.dtu.grp08.dtupay.models;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Muhamad Hussein Nadali (s233479)
 */
@Data
public class Payment {
    private BankAccountNo debtor;
    private BankAccountNo creditor;
    private BigDecimal amount;
}
