package dk.dtu.grp08.reporting.domain.models.user;


import lombok.Data;

@Data
public class UserAccount {
    UserAccountId id = UserAccountId.randomId();
    String firstName;
    String lastName;
    String cpr;
    BankAccountNo bankAccountNo;
}
