package dk.dtu.grp08.account.domain.models.user;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.*;

/**
 * This class represents a User, e.g. a Customer or a Merchant.
 */
@Data
@ToString
@RegisterForReflection
public class UserAccount {
    UserAccountId id;
    String name;
    String cpr;
    BankAccountNo bankAccountNo;
}

