package dk.dtu.grp08.dtupay.models;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Alexander Matzen (s233475)
 */
@Data
@NoArgsConstructor
public class UserAccount {
    UserId id;
    String name;
    String cpr;
    BankAccountNo bankAccountNo;
}
