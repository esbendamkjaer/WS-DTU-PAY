package dk.dtu.grp08.merchant.domain.events;

import dk.dtu.grp08.merchant.domain.models.CorrelationId;
import dk.dtu.grp08.merchant.domain.models.Payment;
import lombok.Value;
/**
 * @author Clair Norah Mutebi (s184187)
 */
@Value
public class PaymentTransferredEvent {
    CorrelationId correlationId;
    Payment payment;
}
