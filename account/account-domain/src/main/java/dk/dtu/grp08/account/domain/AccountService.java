package dk.dtu.grp08.account.domain;

import dk.dtu.grp08.account.domain.models.UserAccount;

public class AccountService {

    public UserAccount registerAccount(
        String firstName,
        String lastName,
        String cpr,
        String bankAccountNo
    ) {
        UserAccount userAccount = new UserAccount(
            firstName,
            lastName,
            cpr,
            bankAccountNo
        );

        return userAccount;
    }

}
