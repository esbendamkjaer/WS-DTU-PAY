package dk.dtu.grp08.dtupay.merchant;

import dk.dtu.grp08.dtupay.models.BankAccountNo;
import dk.dtu.grp08.dtupay.models.UserAccount;
import dk.dtu.grp08.dtupay.models.UserId;
import dk.dtu.grp08.dtupay.models.Payment;
import dk.dtu.grp08.dtupay.models.PaymentRequest;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IMerchantFacade {

    /**
     * @author Fuad
     */
    UserAccount register(
        String name,
        String cpr,
        BankAccountNo bankAccountNo
    );

    /**
     * @author Clair
     */
    void deregister(UserId user);

    /**
     * @author Dilara
     */
    Payment pay(
        PaymentRequest paymentRequest
    );


    /**
     * @author Fuad
     */
    List<Payment> getReport(UserId userId);


}
