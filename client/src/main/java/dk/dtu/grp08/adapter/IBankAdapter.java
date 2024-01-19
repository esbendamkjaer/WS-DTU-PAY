package dk.dtu.grp08.adapter;

import dk.dtu.grp08.dtupay.models.BankAccountNo;

import java.math.BigDecimal;

public interface IBankAdapter {

    /**
     * @author Muhamad
     */
    BankAccountNo createBankAccount(
        String firstName,
        String lastName,
        String cpr,
        BigDecimal balance
    );

    /**
     * @author Alexander
     */
    void retireBankAccount(BankAccountNo bankAccountNo);

    /**
     * @author Fuad
     */
    BigDecimal getBalance(BankAccountNo bankAccountNo);

}
