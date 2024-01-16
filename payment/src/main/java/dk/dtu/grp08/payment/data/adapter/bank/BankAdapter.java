package dk.dtu.grp08.payment.data.adapter.bank;

import dk.dtu.grp08.payment.data.adapter.bank.stub.BankService;
import dk.dtu.grp08.payment.data.adapter.bank.stub.BankServiceException_Exception;
import dk.dtu.grp08.payment.data.adapter.bank.stub.BankServiceService;
import dk.dtu.grp08.payment.domain.adapters.IBankAdapter;
import dk.dtu.grp08.payment.domain.exceptions.NoSuchCreditorAccountException;
import dk.dtu.grp08.payment.domain.exceptions.NoSuchDebtorAccountException;
import dk.dtu.grp08.payment.domain.models.payment.Payment;

public class BankAdapter implements IBankAdapter {

    @Override
    public void makeBankTransfer(Payment payment) {
        BankService bank =  new BankServiceService().getBankServicePort();
        try {
            bank.transferMoneyFromTo(
                payment.getDebtor().getBankAccountNo(),
                payment.getCreditor().getBankAccountNo(),
                payment.getAmount(),
                getPaymentDescription(payment)
            );
        } catch (BankServiceException_Exception e) {
            // @TODO map exceptions to domain exceptions
            switch (e.getMessage()) {
                case "Debtor account does not exist":
                    throw new NoSuchDebtorAccountException();
                case "Creditor account does not exist":
                    throw new NoSuchCreditorAccountException();
                default:
                    throw new RuntimeException(e);
            }
        }
    }

    private String getPaymentDescription(Payment payment) {
        return "Payment from " +
                payment.getDebtor() +
                " to " +
                payment.getCreditor() +
                " for " +
                payment.getAmount() +
                " DKK";
    }

}
