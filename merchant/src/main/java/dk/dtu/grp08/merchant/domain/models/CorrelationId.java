package dk.dtu.grp08.merchant.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
/**
 * @author Dilara Eda Celepli (s184262)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorrelationId {

    UUID id;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof CorrelationId))
            return false;
        if (obj == this)
            return true;
        return this.getId().equals(((CorrelationId) obj).getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public static CorrelationId randomId() {
        return new CorrelationId(UUID.randomUUID());
    }

}
