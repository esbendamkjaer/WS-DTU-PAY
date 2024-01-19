package dk.dtu.grp08.merchant.domain.models;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author Esben Damkjær Sørensen (s233474)
 */
@Data
@NoArgsConstructor
public class UserAccount {
    UserId id;
    String name;
    String cpr;
    BankAccountNo bankAccountNo;
}
