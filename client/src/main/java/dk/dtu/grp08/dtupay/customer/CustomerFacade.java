package dk.dtu.grp08.dtupay.customer;

import dk.dtu.grp08.dtupay.api.ICustomerAPI;
import dk.dtu.grp08.dtupay.Stub;
import dk.dtu.grp08.dtupay.models.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class CustomerFacade implements ICustomerFacade {

    private final ICustomerAPI customerAPI;

    public CustomerFacade() {
        customerAPI = Stub.get(
            ICustomerAPI.class,
            Optional
                .ofNullable(System.getenv("CUSTOMER_API"))
                .orElse("http://localhost:8082")
        );
    }

    @Override
    public List<Token> getTokens(
        UserId userId,
        int count
    ) {
        return customerAPI.getTokens(
            userId.getId(),
            count
        );
    }

    @Override
    public UserAccount register(
        String name,
        String cpr,
        BankAccountNo bankAccountNo
    ) {
        UserAccount userAccount = new UserAccount();
        userAccount.setName(name);
        userAccount.setCpr(cpr);
        userAccount.setBankAccountNo(bankAccountNo);

        return this.customerAPI.createCustomer(
            userAccount
        );
    }

    @Override
    public void deregister(
        UserId userId
    ) {
        this.customerAPI.deleteCustomer(
            userId.getId()
        );
    }

    @Override
    public UserAccount getCustomer(UserId userId) {
        return this.customerAPI.getCustomer(
            userId.getId()
        );
    }

    @Override
    public CompletableFuture<List<Payment>> getReport(UserId userId) {
        return this.customerAPI.getReport(userId);
    }


}
