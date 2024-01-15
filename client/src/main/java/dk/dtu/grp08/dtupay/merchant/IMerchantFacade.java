package dk.dtu.grp08.dtupay.merchant;

import dk.dtu.grp08.stubs.account.models.user.BankAccountNo;
import dk.dtu.grp08.stubs.account.models.user.UserAccount;
import dk.dtu.grp08.stubs.account.models.user.UserId;
import dk.dtu.grp08.stubs.payment.models.Payment;
import dk.dtu.grp08.stubs.payment.models.PaymentRequest;

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
