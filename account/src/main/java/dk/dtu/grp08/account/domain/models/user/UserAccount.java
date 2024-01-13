package dk.dtu.grp08.account.domain.models.user;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.*;

/**
 * This class represents a User, e.g. a Customer or a Merchant.
 */
@Setter
@Getter
@Value
@ToString
@RegisterForReflection
public class UserAccount {
    UserAccountId id = UserAccountId.randomId();
    @NonNull String name;
    @NonNull String cpr;
    @NonNull BankAccountNo bankAccountNo;
}

