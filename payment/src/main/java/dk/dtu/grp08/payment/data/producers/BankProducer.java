package dk.dtu.grp08.payment.data.producers;

import dk.dtu.grp08.payment.data.adapter.bank.BankAdapter;
import dk.dtu.grp08.payment.domain.adapters.IBankAdapter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

public class BankProducer {

    /**
     * @author Fuad
     */
    @Produces
    @ApplicationScoped
    public IBankAdapter bankAdapter() {
        return new BankAdapter();
    }

}
