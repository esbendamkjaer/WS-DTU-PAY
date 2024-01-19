package dk.dtu.grp08.account.domain.models.user;

import lombok.*;

import java.util.UUID;

/**
 * @author Muhamad
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountId {
    UUID id;

    public static UserAccountId randomId() {
        return new UserAccountId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccountId userAccountId)) return false;
        return id.equals(userAccountId.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
