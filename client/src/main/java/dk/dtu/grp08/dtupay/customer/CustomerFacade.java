package dk.dtu.grp08.dtupay.customer;

import dk.dtu.grp08.dtupay.api.ICustomerAPI;
import dk.dtu.grp08.dtupay.Stub;
import dk.dtu.grp08.dtupay.models.BankAccountNo;
import dk.dtu.grp08.dtupay.models.Token;
import dk.dtu.grp08.dtupay.models.UserAccount;
import dk.dtu.grp08.dtupay.models.UserId;

import java.util.List;

public class CustomerFacade implements ICustomerFacade {

    private final ICustomerAPI customerAPI;

    public CustomerFacade() {
        customerAPI = Stub.get(
            ICustomerAPI.class,
            "http://localhost:8083"
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

}
