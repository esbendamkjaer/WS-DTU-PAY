package dk.dtu.grp08.customer.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    /**
     *
     * @author Dilara
     */
    Token token;
    UUID merchantId;
    BigDecimal amount;
}
