package dk.dtu.grp08.adapter;

import dk.dtu.grp08.dtupay.models.BankAccountNo;

import java.math.BigDecimal;

public interface IBankAdapter {

    /**
     * @author Muhamad Hussein Nadali (s233479)
     */
    BankAccountNo createBankAccount(
        String firstName,
        String lastName,
        String cpr,
        BigDecimal balance
    );

    /**
     * @author Alexander Matzen (s233475)
     */
    void retireBankAccount(BankAccountNo bankAccountNo);

    /**
     * @author Fuad Hassan Jama (s233468)
     */
    BigDecimal getBalance(BankAccountNo bankAccountNo);

}
