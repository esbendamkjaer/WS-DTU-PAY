package dk.dtu.grp08.reporting.domain.models.user;

import lombok.Value;

import java.util.UUID;

@Value
public class UserAccountId {
    UUID id;

    public static UserAccountId randomId() {
        return new UserAccountId(UUID.randomUUID());
    }
}
