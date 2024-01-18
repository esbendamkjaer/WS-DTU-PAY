package dk.dtu.grp08.customer.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {
    UserAccountId id;
    String name;
    String cpr;
    BankAccountNo bankAccountNo;
}
