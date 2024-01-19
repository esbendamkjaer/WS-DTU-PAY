package dk.dtu.grp08.customer.domain.models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Payment {

    /**
     *
     * @author Dilara Eda Celepli (s184262)
     */
    private BankAccountNo debtor;
    private BankAccountNo creditor;
    private BigDecimal amount;
}
