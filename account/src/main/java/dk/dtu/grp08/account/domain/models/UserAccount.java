package dk.dtu.grp08.account.domain.models;

import lombok.*;

/**
 * This class represents a User, e.g. a Customer or a Merchant.
 */
@Setter
@Getter
@RequiredArgsConstructor
public class UserAccount {
    UserAccountId id = UserAccountId.randomId();
    @NonNull String name;
    @NonNull String cpr;
    @NonNull String bankAccountNo;

}

