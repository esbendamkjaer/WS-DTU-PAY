package dk.dtu.grp08.account.domain.models;

import lombok.Value;

import java.util.UUID;

/**
 * @author Esben
 */
@Value
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
}
