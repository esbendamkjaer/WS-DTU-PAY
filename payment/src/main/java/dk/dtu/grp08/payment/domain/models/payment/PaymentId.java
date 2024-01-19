package dk.dtu.grp08.payment.domain.models.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Muhamad
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentId {
    private UUID id;
}
