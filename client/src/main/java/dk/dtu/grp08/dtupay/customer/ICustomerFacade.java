package dk.dtu.grp08.dtupay.customer;

import dk.dtu.grp08.dtupay.models.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ICustomerFacade {

    /**
     * @author Dilara Eda Celepli (s184262)
     */
    List<Token> getTokens(UserId userId, int count);

    /**
     * @author Esben Damkjær Sørensen (s233474)
     */
    UserAccount register(
        String name,
        String cpr,
        BankAccountNo bankAccountNo
    );

    /**
     * @author Esben Damkjær Sørensen (s233474)
     */
    void deregister(UserId user);

    /**
     * @author Muhamad Hussein Nadali (s233479)
     */
    UserAccount getCustomer(UserId userId);

    /**
     * @author Alexander Matzen (s233475)
     */
    List<Payment> getReport(UserId userId);
}
