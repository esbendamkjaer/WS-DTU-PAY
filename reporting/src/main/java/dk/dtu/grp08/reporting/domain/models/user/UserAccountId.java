package dk.dtu.grp08.reporting.domain.models.user;

import lombok.Value;

import java.util.UUID;

@Value
public class UserAccountId {
    UUID id;

    public static UserAccountId randomId() {
        return new UserAccountId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserAccountId) {
            return ((UserAccountId) obj).getId().equals(this.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
