package dk.dtu.grp08.payment.data.adapter.bank;

import dk.dtu.grp08.payment.data.adapter.bank.stub.BankService;
import dk.dtu.grp08.payment.data.adapter.bank.stub.BankServiceService;
import dk.dtu.grp08.payment.domain.adapters.IBankAdapter;
import dk.dtu.grp08.payment.domain.models.payment.Payment;

public class BankAdapter implements IBankAdapter {

    @Override
    public void makeBankTransfer(Payment payment) {
        BankService bank =  new BankServiceService().getBankServicePort();

    }

}
