package dk.dtu.grp08.adapter;

import dk.dtu.grp08.stubs.account.models.user.BankAccountNo;

import java.math.BigDecimal;

public interface IBankAdapter {

    BankAccountNo createBankAccount(
        String firstName,
        String lastName,
        String cpr,
        BigDecimal balance
    );

    void retireBankAccount(BankAccountNo bankAccountNo);

    BigDecimal getBalance(BankAccountNo bankAccountNo);

}
