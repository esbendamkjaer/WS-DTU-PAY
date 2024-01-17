package dk.dtu.grp08.dtupay.customer;

import dk.dtu.grp08.dtupay.models.BankAccountNo;
import dk.dtu.grp08.dtupay.models.UserAccount;
import dk.dtu.grp08.dtupay.models.UserId;
import dk.dtu.grp08.dtupay.models.Token;

import java.util.List;

public interface ICustomerFacade {

    List<Token> getTokens(UserId userId, int count);

    UserAccount register(
        String name,
        String cpr,
        BankAccountNo bankAccountNo
    );

    void deregister(UserId user);

    UserAccount getCustomer(UserId userId);

}
