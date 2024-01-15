package dk.dtu.grp08.dtupay.merchant;

import dk.dtu.grp08.dtupay.models.BankAccountNo;
import dk.dtu.grp08.dtupay.models.UserAccount;
import dk.dtu.grp08.dtupay.models.UserId;
import dk.dtu.grp08.dtupay.models.Payment;
import dk.dtu.grp08.dtupay.models.PaymentRequest;

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

}
