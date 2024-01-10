package dk.dtu.grp08.payment.data.adapters;

import dk.dtu.grp08.payment.domain.adapters.IBankAdapter;
import dk.dtu.grp08.payment.domain.models.payment.Payment;

public class BankAdapter implements IBankAdapter {

    @Override
    public void bankTransfering(Payment payment) {
        BankService bank =  new BankServiceService().getBankServicePort();

    }

}
