package dk.dtu.grp08.reporting.data.repositories;


import dk.dtu.grp08.reporting.domain.models.Token;
import dk.dtu.grp08.reporting.domain.models.payment.Payment;
import dk.dtu.grp08.reporting.domain.models.user.UserAccountId;
import dk.dtu.grp08.reporting.domain.repositories.IReportRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ReportRepository implements IReportRepository {

    List<Payment> payments;

    public ReportRepository() {
        payments  = new ArrayList<>();
    }


    @Override
    public List<Payment> getPaymentsByCustomer(UserAccountId customerID) {
        return payments
                .stream()
                .filter(entry -> entry.getDebtor() == customerID).toList();
    }
    public List<Payment> getPaymentsByMerchant(UserAccountId merchantID) {
        return payments
                .stream()
                .filter(entry -> entry.getCreditor() == merchantID)
                .peek(payment -> payment.setDebtor(null)).toList();
    }

    @Override
    public void savePayment(Payment payment) {
        payments.add(payment);
    }

    public void deletePayment(Payment payment) {
        payments.remove(payment);
    }

    public List<Payment> getPayments() {
        return payments;
    }


}
