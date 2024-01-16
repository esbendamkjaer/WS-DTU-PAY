package dk.dtu.grp08.merchant.presentation.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserAccount {
    UserId id;
    String name;
    String cpr;
    BankAccountNo bankAccountNo;
}
