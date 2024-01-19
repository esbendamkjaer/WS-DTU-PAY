package dk.dtu.grp08.dtupay.merchant;

import dk.dtu.grp08.dtupay.models.BankAccountNo;
import dk.dtu.grp08.dtupay.models.UserAccount;
import dk.dtu.grp08.dtupay.models.UserId;
import dk.dtu.grp08.dtupay.models.Payment;
import dk.dtu.grp08.dtupay.models.PaymentRequest;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IMerchantFacade {

    UserAccount register(
        String name,
        String cpr,
        BankAccountNo bankAccountNo
    );

    void deregister(UserId user);

    Payment pay(
        PaymentRequest paymentRequest
    );


    List<Payment> getReport(UserId userId);


}
