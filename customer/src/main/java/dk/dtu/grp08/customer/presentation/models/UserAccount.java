package dk.dtu.grp08.customer.presentation.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class UserAccount {
    UserId id;
    String name;
    String cpr;
    BankAccountNo bankAccountNo;
}
