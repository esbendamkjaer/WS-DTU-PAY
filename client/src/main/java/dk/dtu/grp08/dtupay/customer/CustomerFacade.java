package dk.dtu.grp08.dtupay.customer;

import dk.dtu.grp08.stubs.Stub;
import dk.dtu.grp08.stubs.account.IAccountResource;
import dk.dtu.grp08.stubs.account.models.user.BankAccountNo;
import dk.dtu.grp08.stubs.account.models.user.UserAccount;
import dk.dtu.grp08.stubs.account.models.user.UserId;
import dk.dtu.grp08.stubs.token.ITokenResource;
import dk.dtu.grp08.stubs.token.models.Token;

import java.util.List;

public class CustomerFacade implements ICustomerFacade {

    private final ITokenResource tokenResource;
    private final IAccountResource accountResource;

    public CustomerFacade() {
        tokenResource = Stub.get(ITokenResource.class, "http://localhost:8082");
        accountResource = Stub.get(IAccountResource.class, "http://localhost:8081");
    }

    @Override
    public List<Token> getTokens(
        UserId userId,
        int count
    ) {
        return tokenResource.getTokens(
            count,
            userId.getId()
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

        return this.accountResource.createUserAccount(
            userAccount
        );
    }

    @Override
    public void deregister(UserId user) {
        this.accountResource.deleteUserAccount(
            user.getId()
        );
    }

}
