package dk.dtu.grp08.reporting.domain.events;


import dk.dtu.grp08.reporting.domain.models.Token;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransferEvent {
    UUID merchantID;
    Token token;
    BigDecimal amount;
}
