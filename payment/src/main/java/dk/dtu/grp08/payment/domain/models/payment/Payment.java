package dk.dtu.grp08.payment.domain.models.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private BankAccountNo debtor;
    private BankAccountNo creditor;
    private BigDecimal amount;
}
