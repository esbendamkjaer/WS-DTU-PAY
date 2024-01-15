package dk.dtu.grp08.dtupay.merchant;

import dk.dtu.grp08.stubs.Stub;
import dk.dtu.grp08.stubs.account.IAccountResource;
import dk.dtu.grp08.stubs.account.models.user.BankAccountNo;
import dk.dtu.grp08.stubs.account.models.user.UserAccount;
import dk.dtu.grp08.stubs.account.models.user.UserId;
import dk.dtu.grp08.stubs.payment.IPaymentResource;
import dk.dtu.grp08.stubs.payment.models.Payment;
import dk.dtu.grp08.stubs.payment.models.PaymentRequest;
import dk.dtu.grp08.stubs.token.models.Token;

import java.math.BigDecimal;

public class MerchantFacade implements IMerchantFacade {

    private final IAccountResource accountResource;
    private final IPaymentResource paymentResource;

    public MerchantFacade() {
        this.accountResource = Stub.get(IAccountResource.class, "http://localhost:8081");
        this.paymentResource = Stub.get(IPaymentResource.class, "http://localhost:8080");
    }

    @Override
    public UserAccount register(String name, String cpr, BankAccountNo bankAccountNo) {
        UserAccount userAccount = new UserAccount();
        userAccount.setName(name);
        userAccount.setCpr(cpr);
        userAccount.setBankAccountNo(bankAccountNo);

        return accountResource.createUserAccount(
            userAccount
        );
    }

    @Override
    public void deregister(UserId user) {
        accountResource.deleteUserAccount(
            user.getId()
        );
    }

    @Override
    public Payment pay(PaymentRequest paymentRequest) {
        return this.paymentResource.createPayment(
            paymentRequest
        );
    }

    public Payment pay(Token token, UserId merchantId, BigDecimal amount) {
        return paymentResource.createPayment(
            new PaymentRequest(
                token,
                merchantId.getId(),
                amount
            )
        );
    }
}
