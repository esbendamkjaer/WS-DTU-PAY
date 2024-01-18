package dk.dtu.grp08.merchant.presentation;

import dk.dtu.grp08.merchant.presentation.contracts.IAccountAPI;
import dk.dtu.grp08.merchant.presentation.contracts.IMerchantResource;
import dk.dtu.grp08.merchant.presentation.contracts.IPaymentAPI;
import dk.dtu.grp08.merchant.presentation.models.Payment;
import dk.dtu.grp08.merchant.presentation.models.PaymentRequest;
import dk.dtu.grp08.merchant.presentation.models.UserAccount;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.UUID;

@ApplicationScoped
public class MerchantResource implements IMerchantResource {

    @RestClient
    private IAccountAPI accountResource;

    @RestClient
    private IPaymentAPI paymentResource;

    public MerchantResource() {
    }

    @Override
    public UserAccount createMerchant(UserAccount userAccount) {
        return this.accountResource.createUserAccount(
            userAccount
        );
    }

    @Override
    public void deleteMerchant(UUID userId) {
        this.accountResource.deleteUserAccount(
            userId
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
