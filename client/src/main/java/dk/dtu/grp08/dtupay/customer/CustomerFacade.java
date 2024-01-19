package dk.dtu.grp08.dtupay.customer;

import dk.dtu.grp08.dtupay.api.ICustomerAPI;
import dk.dtu.grp08.dtupay.Stub;
import dk.dtu.grp08.dtupay.models.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class CustomerFacade implements ICustomerFacade {

    private final ICustomerAPI customerAPI;

    /**
     * @author Dilara
     */
    public CustomerFacade() {
        customerAPI = Stub.get(
            ICustomerAPI.class,
            Optional
                .ofNullable(System.getenv("CUSTOMER_API"))
                .orElse("http://localhost:8082")
        );
    }

    /**
     * @author Dilara
     */
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

    /**
     * @author Esben
     */
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

    /**
     * @author Esben
     */
    @Override
    public void deregister(
        UserId userId
    ) {
        this.customerAPI.deleteCustomer(
            userId.getId()
        );
    }

    /**
     * @author Muhamad
     */
    @Override
    public UserAccount getCustomer(UserId userId) {
        return this.customerAPI.getCustomer(
            userId.getId()
        );
    }

    /**
     * @author Alexander
     */
    @Override
    public List<Payment> getReport(UserId userId) {
        return this.customerAPI.getReport(userId.getId());
    }


}
