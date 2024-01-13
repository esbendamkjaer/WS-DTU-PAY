package dk.dtu.grp08.account.domain.models.useraccount;

import lombok.*;

import java.util.UUID;

/**
 * This class represents a User, e.g. a Customer or a Merchant.
 */
@Setter
@Getter
@RequiredArgsConstructor
public class UserAccount {
    UserId id = UserId.randomId();
    String name;
    String cpr;
    BankAccountNo bankAccountNo;

}

