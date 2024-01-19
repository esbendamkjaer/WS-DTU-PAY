package dk.dtu.grp08.account.domain.models.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Clair Norah Mutebi (s184187)
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

