package dk.dtu.grp08.reporting.domain.services;


import dk.dtu.grp08.reporting.domain.models.Token;
import dk.dtu.grp08.reporting.domain.models.payment.Payment;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface IReportService {


    void savePayment(
            final UUID merchantID,
            final Token customerID,
            final BigDecimal amount
    );


    List<Payment> getReportCustomer(Token token);

    List<Payment> getReportMerchant(UUID uuid);

    List<Payment> getReportManager();

}
