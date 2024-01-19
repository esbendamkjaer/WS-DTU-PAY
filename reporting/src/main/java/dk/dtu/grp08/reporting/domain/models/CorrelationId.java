package dk.dtu.grp08.reporting.domain.models;

import lombok.Value;

import java.util.UUID;
/**
 * @author Fuad Hassan Jama (s233468)
 */

@Value
public class CorrelationId {
    UUID id;

    public static CorrelationId randomId() {
        return new CorrelationId(UUID.randomUUID());
    }
}
