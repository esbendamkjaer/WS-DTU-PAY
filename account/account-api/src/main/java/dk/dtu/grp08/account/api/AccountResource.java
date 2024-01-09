package dk.dtu.grp08.account.api;

import dk.dtu.grp08.account.api.contracts.IAccountResource;
import dk.dtu.grp08.account.domain.models.UserAccount;

public class AccountResource implements IAccountResource {

    @Override
    public UserAccount createUserAccount(UserAccount userAccount) {
        return userAccount;
    }

}
