package dk.dtu.grp08.payment.domain.events;

import dk.dtu.grp08.payment.domain.models.CorrelationId;
import dk.dtu.grp08.payment.domain.models.Token;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Alexander Matzen (s233475)
 */
@Value
public class PaymentInitiatedEvent {
    UUID merchantID;
    Token token;
    BigDecimal amount;
    CorrelationId correlationId;
}
