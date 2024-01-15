package dk.dtu.grp08.account.domain.models.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * This class represents a User, e.g. a Customer or a Merchant.
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {
    UserAccountId id;
    String name;
    String cpr;
    BankAccountNo bankAccountNo;
}

