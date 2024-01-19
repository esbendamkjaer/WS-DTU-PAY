package dk.dtu.grp08.reporting.domain.services;


import dk.dtu.grp08.reporting.domain.models.Token;
import dk.dtu.grp08.reporting.domain.models.payment.Payment;
import dk.dtu.grp08.reporting.domain.models.user.UserAccountId;
import dk.dtu.grp08.reporting.domain.repositories.IReportRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
/**
 * @author Alexander Matzen (s233475)
 */
@ApplicationScoped
public class ReportService implements IReportService {


    private final IReportRepository reportRepository;



    public ReportService(IReportRepository reportRepository) {

        this.reportRepository = reportRepository;

    }
    /**
     * @author Fuad Hassan Jama (s233468)
     */
    @Override
    public void savePayment(
        final UserAccountId merchantID,
        final UserAccountId token,
        final BigDecimal amount
    ) {

        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setDebtor(token);
        payment.setCreditor(merchantID);
        reportRepository.savePayment(payment);

    }

    @Override
    public List<Payment> getReportCustomer(UserAccountId customerID) {
        //Customer Formatted report
        return reportRepository.getPaymentsByCustomer(customerID);
    }

    /**
     * @author Dilara Eda Celepli (s184262)
     */
    @Override
    public List<Payment> getReportMerchant(UserAccountId merchantID) {
        //merchant formatted report
        return reportRepository.getPaymentsByMerchant(merchantID);


    }

    /**
     * @author Dilara Eda Celepli (s184262)
     */
    @Override
    public List<Payment> getReportManager() {
        //manager formatted report
        return reportRepository.getPayments();

    }


}
