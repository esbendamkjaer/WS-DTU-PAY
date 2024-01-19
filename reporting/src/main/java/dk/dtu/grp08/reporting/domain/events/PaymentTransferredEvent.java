package dk.dtu.grp08.reporting.domain.events;

/**
 * @author Clair Norah Mutebi (s184187)
 */

import dk.dtu.grp08.reporting.domain.models.CorrelationId;
import dk.dtu.grp08.reporting.domain.models.Token;
import dk.dtu.grp08.reporting.domain.models.payment.Payment;
import dk.dtu.grp08.reporting.domain.models.user.UserAccountId;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransferredEvent {
    UserAccountId merchantId;
    UserAccountId customerId;
    Payment payment;
    CorrelationId correlationId;
}
