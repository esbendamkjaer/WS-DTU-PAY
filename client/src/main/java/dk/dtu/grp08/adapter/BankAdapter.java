package dk.dtu.grp08.adapter;

import dk.dtu.grp08.bank.BankService;
import dk.dtu.grp08.bank.BankServiceException_Exception;
import dk.dtu.grp08.bank.BankServiceService;
import dk.dtu.grp08.bank.User;
import dk.dtu.grp08.dtupay.models.BankAccountNo;

import java.math.BigDecimal;

public class BankAdapter implements IBankAdapter {

    private final BankService bankService;

    public BankAdapter() {
        this.bankService = new BankServiceService().getBankServicePort();
    }

    @Override
    public BankAccountNo createBankAccount(
            String firstName,
            String lastName,
            String cpr,
            BigDecimal balance
    ) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setCprNumber(cpr);

        String bankAccountNo = null;
        try {
            bankAccountNo = bankService.createAccountWithBalance(
                user,
                balance
            );
        } catch (BankServiceException_Exception e) {
            // @TODO Add mappings to domain exceptions
            throw new RuntimeException(e);
        }

        return new BankAccountNo(bankAccountNo);
    }

    @Override
    public void retireBankAccount(BankAccountNo bankAccountNo) {
        try {
            this.bankService.retireAccount(
                bankAccountNo.getBankAccountNo()
            );
        } catch (BankServiceException_Exception e) {
            // @TODO Add mappings to domain exceptions
            throw new RuntimeException(e);
        }
    }

    @Override
    public BigDecimal getBalance(BankAccountNo bankAccountNo) {
        try {
            return bankService.getAccount(
                bankAccountNo.getBankAccountNo()
            ).getBalance();
        } catch (BankServiceException_Exception e) {
            // @TODO Add mappings to domain exceptions
            throw new RuntimeException(e);
        }
    }

}
