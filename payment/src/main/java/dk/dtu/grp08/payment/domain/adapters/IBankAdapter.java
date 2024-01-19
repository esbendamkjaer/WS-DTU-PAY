package dk.dtu.grp08.payment.domain.adapters;

import dk.dtu.grp08.payment.domain.models.payment.Payment;

public interface IBankAdapter {

    /**
     * @author Alexander Matzen (s233475)
     */
    public void makeBankTransfer(Payment payment);

}
