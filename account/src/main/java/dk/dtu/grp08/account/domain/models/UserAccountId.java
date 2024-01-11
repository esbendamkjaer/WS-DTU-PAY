package dk.dtu.grp08.account.domain.models;

import lombok.Value;
import java.util.UUID;

@Value
public class UserAccountId {
    UUID id;

    public static UserAccountId randomId() {
        return new UserAccountId(UUID.randomUUID());
    }
}
