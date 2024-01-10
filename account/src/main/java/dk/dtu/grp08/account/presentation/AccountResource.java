package dk.dtu.grp08.account.presentation;

import dk.dtu.grp08.account.presentation.contracts.IAccountResource;
import dk.dtu.grp08.account.domain.AccountService;
import dk.dtu.grp08.account.domain.models.UserAccount;
import jakarta.inject.Inject;

public class AccountResource implements IAccountResource {
    @Inject
    private AccountService accountService;

    @Override
    public UserAccount createUserAccount(UserAccount userAccount) {
        return accountService.registerAccount(
            userAccount.getFirstName(),
            userAccount.getLastName(),
            userAccount.getCpr(),
            userAccount.getBankAccountNo()
        );
    }

}
