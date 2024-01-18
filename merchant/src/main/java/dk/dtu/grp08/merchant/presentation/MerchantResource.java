package dk.dtu.grp08.merchant.presentation;

import dk.dtu.grp08.merchant.domain.models.UserId;
import dk.dtu.grp08.merchant.domain.services.contracts.IAccountService;
import dk.dtu.grp08.merchant.domain.services.contracts.IPaymentService;
import dk.dtu.grp08.merchant.presentation.contracts.IMerchantResource;
import dk.dtu.grp08.merchant.domain.models.Payment;
import dk.dtu.grp08.merchant.domain.models.PaymentRequest;
import dk.dtu.grp08.merchant.domain.models.UserAccount;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class MerchantResource implements IMerchantResource {

    private final IAccountService accountService;
    private final IPaymentService paymentService;

    public MerchantResource(
        IAccountService accountService,
        IPaymentService paymentService
    ) {
        this.accountService = accountService;
        this.paymentService = paymentService;
    }

    @Override
    public CompletableFuture<UserAccount> createMerchant(UserAccount userAccount) {
        return this.accountService.createUserAccount(
            userAccount
        );
    }

    @Override
    public CompletableFuture<Void> deleteMerchant(UUID userId) {
        return this.accountService.deleteUserAccount(
            new UserId(userId)
        );
    }

    @Override
    public CompletableFuture<Payment> createPayment(PaymentRequest paymentRequest) {
        return this.paymentService.createPayment(
            paymentRequest
        );
    }

    @Override
    public void getReport(UUID id) {

    }
}
