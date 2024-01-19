package dk.dtu.grp08.customer.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {
    /**
     *
     * @author Muhamad Hussein Nadali (s233479)
     */
    UserAccountId id;
    String name;
    String cpr;
    BankAccountNo bankAccountNo;
}
