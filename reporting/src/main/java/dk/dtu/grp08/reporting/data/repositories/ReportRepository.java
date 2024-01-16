package dk.dtu.grp08.reporting.data.repositories;


import dk.dtu.grp08.reporting.domain.models.Token;
import dk.dtu.grp08.reporting.domain.models.payment.Payment;
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
    public List<Payment> getPaymentsByToken(Token token) {
        return payments
                .stream()
                .filter(entry -> entry.getDebtor() == token).toList();
    }
    public List<Payment> getPaymentsByMerchant(UUID merchantID) {
        return payments
                .stream()
                .filter(entry -> entry.getCreditor() == merchantID).toList();
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
