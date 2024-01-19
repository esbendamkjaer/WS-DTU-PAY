package dk.dtu.grp08.payment.data.repositories;

import dk.dtu.grp08.payment.domain.models.payment.Payment;
import dk.dtu.grp08.payment.domain.models.payment.PaymentId;
import dk.dtu.grp08.payment.domain.repositories.IPaymentRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PaymentRepository implements IPaymentRepository {

    List<Payment> payments;

    public PaymentRepository() {
        payments = new ArrayList<>();
    }

    /**
     * @author Dilara
     */
    public Payment savePayment(Payment payment) {
        payment = new Payment(
            new PaymentId(UUID.randomUUID()),
            payment.getDebtor(),
            payment.getCreditor(),
            payment.getAmount()
        );

        payments.add(payment);

        return payment;
    }

    /**
     * @author Clair
     */
    public void deletePayment(Payment payment) {
        payments.remove(payment);
    }

    /**
     * Muhamad
     */
    public List<Payment> getPayments() {
        return payments;
    }


}
