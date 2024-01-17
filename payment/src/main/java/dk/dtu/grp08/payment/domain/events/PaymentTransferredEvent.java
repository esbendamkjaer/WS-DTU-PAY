package dk.dtu.grp08.payment.domain.events;


import dk.dtu.grp08.payment.domain.models.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransferredEvent {
    UUID merchantID;
    Token token;
    BigDecimal amount;
}
