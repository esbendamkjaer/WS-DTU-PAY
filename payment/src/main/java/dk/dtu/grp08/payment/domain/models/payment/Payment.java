package dk.dtu.grp08.payment.domain.models.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Clair Norah Mutebi (s184187)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private PaymentId paymentId;
    private BankAccountNo debtor;
    private BankAccountNo creditor;
    private BigDecimal amount;
}
