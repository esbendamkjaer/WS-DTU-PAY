package dk.dtu.grp08.reporting.domain.models.payment;


import dk.dtu.grp08.reporting.domain.models.Token;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class Payment {
    private Token debtor;
    private UUID creditor;
    private BigDecimal amount;
}
