package dk.dtu.grp08.payment.domain.models.user;

import dk.dtu.grp08.payment.domain.models.payment.BankAccountNo;
import lombok.Data;

@Data
public class UserAccount {
    UserAccountId id = UserAccountId.randomId();
    String firstName;
    String lastName;
    String cpr;
    BankAccountNo bankAccountNo;
}
