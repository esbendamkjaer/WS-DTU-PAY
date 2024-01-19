package dk.dtu.grp08.merchant.domain.models;

import lombok.Data;

import java.math.BigDecimal;
/**
 * @author Clair Norah Mutebi (s184187)
 */
@Data
public class Payment {
    private BankAccountNo debtor;
    private BankAccountNo creditor;
    private BigDecimal amount;
}
