package dk.dtu.grp08.customer.presentation.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {
    UserAccountId id;
    String name;
    String cpr;
    BankAccountNo bankAccountNo;
}
