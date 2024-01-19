package dk.dtu.grp08.payment.domain.repositories;


import dk.dtu.grp08.payment.domain.models.payment.Payment;

import java.util.List;

public interface IPaymentRepository {

    /**
     * @author Muhamad
     */
    Payment savePayment(Payment payment);

    /**
     * @author Alexander
     */
    void deletePayment(Payment payment);

    /**
     * @author @Fuad
     */
    List<Payment> getPayments();

}
