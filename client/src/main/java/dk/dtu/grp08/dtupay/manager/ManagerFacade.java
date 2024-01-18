package dk.dtu.grp08.dtupay.merchant;

import dk.dtu.grp08.dtupay.api.IMerchantAPI;
import dk.dtu.grp08.dtupay.Stub;
import dk.dtu.grp08.dtupay.models.BankAccountNo;
import dk.dtu.grp08.dtupay.models.UserAccount;
import dk.dtu.grp08.dtupay.models.UserId;
import dk.dtu.grp08.dtupay.models.Payment;
import dk.dtu.grp08.dtupay.models.PaymentRequest;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class MerchantFacade implements IMerchantFacade {

    private final IMerchantAPI merchantAPI;

    public MerchantFacade() {
        this.merchantAPI = Stub.get(
            IMerchantAPI.class,
            Optional
                .ofNullable(System.getenv("MERCHANT_API"))
                .orElse("http://localhost:8086")
        );
    }

    @Override
    public UserAccount register(String name, String cpr, BankAccountNo bankAccountNo) {
        UserAccount userAccount = new UserAccount();
        userAccount.setName(name);
        userAccount.setCpr(cpr);
        userAccount.setBankAccountNo(bankAccountNo);

        return merchantAPI.createMerchant(
            userAccount
        );
    }

    @Override
    public void deregister(UserId user) {
        merchantAPI.deleteMerchant(
            user.getId()
        );
    }

    @Override
    public Payment pay(PaymentRequest paymentRequest) {
        return this.merchantAPI.createPayment(
            paymentRequest
        );
    }

    @Override
    public CompletableFuture<List<Payment>> getReport(UserId userId) {
        return this.merchantAPI.getReport(userId);
    }
}
