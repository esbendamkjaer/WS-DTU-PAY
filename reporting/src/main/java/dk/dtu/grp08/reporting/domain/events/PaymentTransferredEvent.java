package dk.dtu.grp08.reporting.domain.events;


import dk.dtu.grp08.reporting.domain.models.Token;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Value
public class PaymentTransferredEvent {
    UUID merchantID;
    Token token;
    BigDecimal amount;
}
