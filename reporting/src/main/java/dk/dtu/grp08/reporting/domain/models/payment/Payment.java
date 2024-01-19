package dk.dtu.grp08.reporting.domain.models.payment;


import dk.dtu.grp08.reporting.domain.models.Token;
import dk.dtu.grp08.reporting.domain.models.user.UserAccountId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;
/**
 * @author Esben Damkjær Sørensen (s233474)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private UserAccountId debtor;
    private UserAccountId creditor;
    private BigDecimal amount;
}
