package dk.dtu.grp08.payment.domain.repositories;


import dk.dtu.grp08.payment.domain.models.payment.Payment;

import java.util.List;

public interface IPaymentRepository {

    Payment savePayment(Payment payment);

    void deletePayment(Payment payment);

    List<Payment> getPayments();

}
