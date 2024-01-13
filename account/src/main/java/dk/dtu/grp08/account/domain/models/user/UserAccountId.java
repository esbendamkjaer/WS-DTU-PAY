package dk.dtu.grp08.account.domain.models.user;

import lombok.Value;
import java.util.UUID;

@Value
public class UserAccountId {
    UUID id;

    public static UserAccountId randomId() {
        return new UserAccountId(UUID.randomUUID());
    }

    public static UserAccountId fromString(String id) { return new UserAccountId(UUID.fromString(id)); }
}
