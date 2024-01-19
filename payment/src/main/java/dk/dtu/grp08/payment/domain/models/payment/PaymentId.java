package dk.dtu.grp08.payment.domain.models.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Muhamad Hussein Nadali (s233479)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentId {
    private UUID id;
}
