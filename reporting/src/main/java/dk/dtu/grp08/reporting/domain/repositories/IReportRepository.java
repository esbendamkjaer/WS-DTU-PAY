package dk.dtu.grp08.reporting.domain.repositories;



import dk.dtu.grp08.reporting.domain.models.Token;
import dk.dtu.grp08.reporting.domain.models.payment.Payment;
import dk.dtu.grp08.reporting.domain.models.user.UserAccountId;

import java.util.List;
import java.util.UUID;

public interface IReportRepository {

    List<Payment> getPaymentsByCustomer(UserAccountId customerID);


    List<Payment> getPaymentsByMerchant(UserAccountId merchantID);

    void savePayment(Payment payment);

    void deletePayment(Payment payment);

    List<Payment> getPayments();



}
