package dk.dtu.grp08.account.presentation;

import dk.dtu.grp08.account.presentation.contracts.IAccountResource;
import dk.dtu.grp08.account.domain.services.AccountService;
import dk.dtu.grp08.account.domain.models.UserAccount;
import jakarta.inject.Inject;

public class AccountResource implements IAccountResource {
    @Inject
    AccountService accountService;

    @Override
    public UserAccount createUserAccount(UserAccount userAccount) {
        return accountService.registerAccount(
            userAccount.getName(),
            userAccount.getCpr(),
            userAccount.getBankAccountNo()
        );
    }

}
