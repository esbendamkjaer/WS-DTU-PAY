package dk.dtu.grp08.manager.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * @author Muhamad Hussein Nadali (s233479)
 */
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

    /**
     * @author Alexander Matzen (s233475)
     */

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public static CorrelationId randomId() {
        return new CorrelationId(UUID.randomUUID());
    }

}
