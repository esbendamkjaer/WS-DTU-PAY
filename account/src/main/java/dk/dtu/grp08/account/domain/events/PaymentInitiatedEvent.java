package dk.dtu.grp08.account.domain.events;

import dk.dtu.grp08.account.domain.models.CorrelationId;
import dk.dtu.grp08.account.domain.models.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Dilara Eda Celepli (s184262)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInitiatedEvent {
    UUID merchantID;
    Token token;
    BigDecimal amount;
    CorrelationId correlationId;
}
