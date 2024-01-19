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
     * @author Fuad Hassan Jama (s233468)
     */
    UserAccount register(
        String name,
        String cpr,
        BankAccountNo bankAccountNo
    );

    /**
     * @author Clair Norah Mutebi (s184187)
     */
    void deregister(UserId user);

    /**
     * @author Dilara Eda Celepli (s184262)
     */
    Payment pay(
        PaymentRequest paymentRequest
    );


    /**
     * @author Fuad Hassan Jama (s233468)
     */
    List<Payment> getReport(UserId userId);


}
