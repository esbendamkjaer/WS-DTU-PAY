package dk.dtu.grp08.dtupay.customer;

import dk.dtu.grp08.dtupay.models.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ICustomerFacade {

    List<Token> getTokens(UserId userId, int count);

    UserAccount register(
        String name,
        String cpr,
        BankAccountNo bankAccountNo
    );

    void deregister(UserId user);

    UserAccount getCustomer(UserId userId);

    List<Payment> getReport(UserId userId);
}
