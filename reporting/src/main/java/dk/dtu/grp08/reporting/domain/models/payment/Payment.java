package dk.dtu.grp08.reporting.domain.models.payment;


import dk.dtu.grp08.reporting.domain.models.Token;
import dk.dtu.grp08.reporting.domain.models.user.UserAccountId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class Payment {
    private UserAccountId debtor;
    private UserAccountId creditor;
    private BigDecimal amount;
}
