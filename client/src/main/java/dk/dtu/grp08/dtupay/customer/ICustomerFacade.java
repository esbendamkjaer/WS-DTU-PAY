package dk.dtu.grp08.dtupay.customer;

import dk.dtu.grp08.stubs.account.models.user.BankAccountNo;
import dk.dtu.grp08.stubs.account.models.user.UserAccount;
import dk.dtu.grp08.stubs.account.models.user.UserId;
import dk.dtu.grp08.stubs.token.models.Token;

import java.util.List;

public interface ICustomerFacade {

    List<Token> getTokens(UserId userId, int count);

    UserAccount register(
        String name,
        String cpr,
        BankAccountNo bankAccountNo
    );

    void deregister(UserId user);
}
