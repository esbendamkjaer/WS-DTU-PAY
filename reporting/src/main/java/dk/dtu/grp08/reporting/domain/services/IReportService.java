package dk.dtu.grp08.reporting.domain.services;


import dk.dtu.grp08.reporting.domain.models.Token;
import dk.dtu.grp08.reporting.domain.models.payment.Payment;
import dk.dtu.grp08.reporting.domain.models.user.UserAccountId;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface IReportService {


    void savePayment(
            final UserAccountId merchantID,
            final UserAccountId customerID,
            final BigDecimal amount
    );


    List<Payment> getReportCustomer(UserAccountId customerID);

    List<Payment> getReportMerchant(UserAccountId merchantID);

    List<Payment> getReportManager();

}
