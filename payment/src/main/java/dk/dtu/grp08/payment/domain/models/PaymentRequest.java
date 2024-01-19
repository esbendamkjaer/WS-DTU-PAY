package dk.dtu.grp08.payment.domain.models;

import dk.dtu.grp08.payment.domain.models.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Clair
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    Token token;
    UUID merchantId;
    BigDecimal amount;
}
