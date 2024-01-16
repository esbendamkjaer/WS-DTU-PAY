package dk.dtu.grp08.reporting.domain.repositories;



import dk.dtu.grp08.reporting.domain.models.Token;
import dk.dtu.grp08.reporting.domain.models.payment.Payment;

import java.util.List;
import java.util.UUID;

public interface IReportRepository {

    List<Payment> getPaymentsByToken(Token token);


    List<Payment> getPaymentsByMerchant(UUID merchantID);

    void savePayment(Payment payment);

    void deletePayment(Payment payment);

    List<Payment> getPayments();



}
