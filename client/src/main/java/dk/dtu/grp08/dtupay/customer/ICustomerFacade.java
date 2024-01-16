package dk.dtu.grp08.dtupay.customer;

import dk.dtu.grp08.dtupay.models.BankAccountNo;
import dk.dtu.grp08.dtupay.models.UserAccount;
import dk.dtu.grp08.dtupay.models.UserId;
import dk.dtu.grp08.dtupay.models.Token;

import java.util.List;
import java.util.Optional;

public interface ICustomerFacade {

    List<Token> getTokens(UserId userId, int count);

    UserAccount register(
        String name,
        String cpr,
        BankAccountNo bankAccountNo
    );

    void deregister(UserId user);

    Optional<UserAccount> getCustomer(UserId userId);

}
