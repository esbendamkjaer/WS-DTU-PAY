package dk.dtu.grp08.reporting.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;
/**
 * @author Dilara Eda Celepli (s184262)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    Token token;
    UUID merchantId;
    BigDecimal amount;
}
