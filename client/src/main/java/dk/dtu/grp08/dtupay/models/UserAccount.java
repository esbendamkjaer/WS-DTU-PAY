package dk.dtu.grp08.dtupay.models;

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