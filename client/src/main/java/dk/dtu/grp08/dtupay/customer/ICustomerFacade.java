package dk.dtu.grp08.dtupay.customer;

import dk.dtu.grp08.dtupay.models.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ICustomerFacade {

    /**
     * @author Dilara
     */
    List<Token> getTokens(UserId userId, int count);

    /**
     * @author Esben
     */
    UserAccount register(
        String name,
        String cpr,
        BankAccountNo bankAccountNo
    );

    /**
     * @author Esben
     */
    void deregister(UserId user);

    /**
     * @author Muhamad
     */
    UserAccount getCustomer(UserId userId);

    /**
     * @author Alexander
     */
    List<Payment> getReport(UserId userId);
}
