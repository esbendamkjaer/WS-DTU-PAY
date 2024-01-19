package dk.dtu.grp08.merchant.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;
/**
 * @author Clair Norah Mutebi (s184187)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    Token token;
    UUID merchantId;
    BigDecimal amount;
}
