package dk.dtu.grp08.merchant.presentation;

import dk.dtu.grp08.merchant.domain.models.UserId;
import dk.dtu.grp08.merchant.domain.services.contracts.IAccountService;
import dk.dtu.grp08.merchant.presentation.contracts.IMerchantResource;
import dk.dtu.grp08.merchant.presentation.contracts.IPaymentAPI;
import dk.dtu.grp08.merchant.domain.models.Payment;
import dk.dtu.grp08.merchant.domain.models.PaymentRequest;
import dk.dtu.grp08.merchant.domain.models.UserAccount;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class MerchantResource implements IMerchantResource {

    @RestClient
    private IPaymentAPI paymentResource;

    private final IAccountService accountService;

    public MerchantResource(IAccountService accountService) {
        this.accountService = accountService;
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
    public Payment createPayment(PaymentRequest paymentRequest) {
        return this.paymentResource.createPayment(
            paymentRequest
        );
    }

    @Override
    public void getReport(UUID id) {

    }
}
