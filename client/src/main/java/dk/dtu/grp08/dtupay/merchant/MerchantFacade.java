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

    /**
     * @author Dilara Eda Celepli (s184262)
     */
    public MerchantFacade() {
        this.merchantAPI = Stub.get(
            IMerchantAPI.class,
            Optional
                .ofNullable(System.getenv("MERCHANT_API"))
                .orElse("http://localhost:8086")
        );
    }

    /**
     * @author Fuad Hassan Jama (s233468)
     */
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

    /**
     * @author Clair Norah Mutebi (s184187)
     */
    @Override
    public void deregister(UserId user) {
        merchantAPI.deleteMerchant(
            user.getId()
        );
    }

    /**
     * @author Dilara Eda Celepli (s184262)
     */
    @Override
    public Payment pay(PaymentRequest paymentRequest) {
        return this.merchantAPI.createPayment(
            paymentRequest
        );
    }

    /**
     * @author Fuad Hassan Jama (s233468)
     */
    @Override
    public List<Payment> getReport(UserId userId) {
        return this.merchantAPI.getReport(userId.getId());
    }
}
