package dk.dtu.grp08.payment.domain.events;


import dk.dtu.grp08.payment.domain.models.CorrelationId;
import dk.dtu.grp08.payment.domain.models.payment.Payment;
import dk.dtu.grp08.payment.domain.models.user.UserAccountId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransferredEvent {
    CorrelationId correlationId;
    Payment payment;
    UserAccountId merchantId;
    UserAccountId customerId;
}
