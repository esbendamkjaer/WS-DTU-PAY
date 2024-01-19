package dk.dtu.grp08.payment.domain.models.user;

import lombok.Value;

import java.util.UUID;

/**
 * @author Fuad
 */
@Value
public class UserAccountId {
    UUID id;

    public static UserAccountId randomId() {
        return new UserAccountId(UUID.randomUUID());
    }
}
