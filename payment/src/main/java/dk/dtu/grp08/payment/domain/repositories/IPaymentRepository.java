package dk.dtu.grp08.payment.domain.repositories;


import dk.dtu.grp08.payment.domain.models.payment.Payment;

import java.util.List;

public interface IPaymentRepository {

    /**
     * @author Muhamad Hussein Nadali (s233479)
     */
    Payment savePayment(Payment payment);

    /**
     * @author Alexander Matzen (s233475)
     */
    void deletePayment(Payment payment);

    /**
     * @author @Fuad Hassan Jama (s233468)
     */
    List<Payment> getPayments();

}
