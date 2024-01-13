package dk.dtu.grp08.account.domain.models.useraccount;

import lombok.Value;

import java.util.UUID;

@Value
public class UserId {
    UUID id;
    public static UserId randomId() {

    }
}
