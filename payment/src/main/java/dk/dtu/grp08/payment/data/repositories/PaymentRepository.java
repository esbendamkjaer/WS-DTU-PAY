package dk.dtu.grp08.payment.data.repositories;

import dk.dtu.grp08.payment.domain.models.payment.Payment;
import dk.dtu.grp08.payment.domain.repositories.IPaymentRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PaymentRepository implements IPaymentRepository {

    List<Payment> payments;

    public PaymentRepository() {
        payments = new ArrayList<>();
    }

    public void savePayment(Payment payment) {
        payments.add(payment) ;
    }

    public void deletePayment(Payment payment) {
        payments.remove(payment);
    }

    public List<Payment> getPayments() {
        return payments;
    }


}
