package dk.dtu.grp08.payment.domain.models;

import lombok.Value;

import java.util.UUID;

@Value
public class CorrelationId {
    UUID id;

    public static CorrelationId randomId() {
        return new CorrelationId(UUID.randomUUID());
    }
}
