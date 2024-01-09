package dk.dtu.grp08.account.domain.models;

import lombok.*;

import java.util.UUID;

/**
 * This class represents a User, e.g. a Customer or a Merchant.
 */
@Setter
@Getter
@RequiredArgsConstructor
public class UserAccount {
    UUID id = UUID.randomUUID();
    @NonNull String firstName;
    @NonNull String lastName;
    @NonNull String cpr;
    @NonNull String bankAccountNo;

}

