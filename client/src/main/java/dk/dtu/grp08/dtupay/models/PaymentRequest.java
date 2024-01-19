package dk.dtu.grp08.dtupay.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Muhamad Hussein Nadali (s233479)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    Token token;
    UUID merchantId;
    BigDecimal amount;
}
