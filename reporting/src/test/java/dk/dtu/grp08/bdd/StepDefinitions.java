package dk.dtu.grp08.bdd;


import dk.dtu.grp08.reporting.data.repositories.ReportRepository;
import dk.dtu.grp08.reporting.domain.events.*;
import dk.dtu.grp08.reporting.domain.models.CorrelationId;
import dk.dtu.grp08.reporting.domain.models.Token;
import dk.dtu.grp08.reporting.domain.models.payment.Payment;
import dk.dtu.grp08.reporting.domain.models.user.UserAccount;
import dk.dtu.grp08.reporting.domain.models.user.UserAccountId;
import dk.dtu.grp08.reporting.domain.services.ReportService;
import dk.dtu.grp08.reporting.presentation.ReportRessource;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import messaging.MessageQueue;
import org.junit.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class StepDefinitions {

    private final MessageQueue messageQueue = mock(MessageQueue.class);

    private ReportRepository reportRepository = new ReportRepository();

    private ReportService reportService = new ReportService(
            reportRepository
    );
    public ReportRessource reportRessource = new ReportRessource(
            reportService,
            messageQueue
    );


    private CorrelationId correlationId;


    private UserAccountId customerID;
    private UserAccountId merchantID;

    private Payment payment;
    private PaymentTransferredEvent paymentTransferredEvent;

    private CustomerReportRequested customerReportRequestedEvent;
    private MerchantReportRequested merchantReportRequestedEvent;


    private ManagerReportRequested managerReportRequestedEvent;


    EventType eventType = EventType.PAYMENT_TRANSFERRED;

    List<Payment> report;

    /**
     * @author Alexander Matzen (s233475)
     */
    @Given("a Customer with id {string}")
    public void setCustomer(String customerID) {
        this.customerID = new UserAccountId(UUID.fromString(customerID));

    }

    /**
     * @author Alexander Matzen (s233475)
     */
    @And("a Merchant with id {string}")
    public void setMerchant( String merchantID) {
        this.merchantID = new UserAccountId(UUID.fromString(merchantID));


    }
    /**
     * @author Esben Damkjær Sørensen (s233474)
     */
    @And("a Payment with {int} kr")
    public void setPayment(int amount) {
        this.payment = new Payment();
        this.payment.setAmount(BigDecimal.valueOf(amount));
        this.payment.setCreditor(this.merchantID);
        this.payment.setDebtor(this.customerID);

    }


    /**
     * @author Esben Damkjær Sørensen (s233474)
     */

    @When("a PAYMENT_TRANSFERRED event is received")
    public void aPaymentTransferredEvent() {

        this.correlationId = CorrelationId.randomId();


        paymentTransferredEvent = new PaymentTransferredEvent(
                merchantID,
                customerID,
                payment,
                correlationId
        );


        this.reportRessource.handlePaymentTransferredEvent(
                new messaging.Event(
                        EventType.PAYMENT_TRANSFERRED.getEventName(),
                        new Object[]{
                                paymentTransferredEvent

                        }
                )
        );
    }

    /**
     * @author Muhamad Hussein Nadali (s233479)
     */


    @Then("the payment should be saved")
    public void thePaymentShouldBeSaved() {


        Payment paymentSaved = reportRepository.getPayments().get(0);



        Assert.assertTrue(paymentSaved.getCreditor().getId().equals(payment.getCreditor().getId()) &&
                payment.getDebtor().getId().equals(paymentSaved.getDebtor().getId()) &&
                Objects.equals(payment.getAmount(), paymentSaved.getAmount()));
    }

    /**
     * @author Muhamad Hussein Nadali (s233479)
     */

    @When("a CUSTOMER_REPORT_REQUESTED event is received")
    public void aCustomerReportRequestedEvent() {

        correlationId = CorrelationId.randomId();


        customerReportRequestedEvent = new CustomerReportRequested(customerID, correlationId);


        this.reportRessource.handleCustomerReportRequested(
                new messaging.Event(
                        EventType.CUSTOMER_REPORT_REQUESTED.getEventName(),
                        new Object[]{
                                customerReportRequestedEvent

                        }
                )
        );

    }

    /**
     * @author Fuad Hassan Jama (s233468)
     */
    @When("a MERCHANT_REPORT_REQUESTED event is received")
    public void aMerchantReportRequestedEvent() {


        correlationId = CorrelationId.randomId();


        merchantReportRequestedEvent = new MerchantReportRequested(merchantID, correlationId);




        this.reportRessource.handleMerchantReportRequested(
                new messaging.Event(
                        EventType.MERCHANT_REPORT_REQUESTED.getEventName(),
                        new Object[]{
                                merchantReportRequestedEvent

                        }
                )
        );

    }


    /**
     * @author Dilara Eda Celepli (s184262)
     */
    @When("a MANAGER_REPORT_REQUESTED event is received")
    public void aManagerReportRequestedEvent() {


        correlationId = CorrelationId.randomId();


        managerReportRequestedEvent = new ManagerReportRequested(correlationId);


        this.reportRessource.handleManagerReportRequested(
                new messaging.Event(
                        EventType.MANAGER_REPORT_REQUESTED.getEventName(),
                        new Object[]{
                                managerReportRequestedEvent

                        }
                )
        );

    }


    /**
     * @author Clair Norah Mutebi (s184187)
     */

    @Then("a report should be generated with all payments for the customer")
    public void aReportGeneratedCustomer() {


        report = reportService.getReportCustomer(customerID);
        boolean isCustomerPayments = report.stream().allMatch(payment -> payment.getDebtor().getId().equals(customerID.getId()));

        Assert.assertTrue(isCustomerPayments);

    }
    /**
     * @author Clair Norah Mutebi (s184187)
     */
    @Then("a report should be generated with all payments to the merchant")
    public void aReportGeneratedMerchant() {


        report = reportService.getReportMerchant(merchantID);
        boolean isMerchantPayments = report.stream().allMatch(payment -> payment.getCreditor().getId().equals(merchantID.getId()));

        Assert.assertTrue(isMerchantPayments);

    }

    /**
     * @author Dilara Eda Celepli (s184262)
     */
    @Then("a report should be generated with all payments")
    public void aReportGeneratedManager() {


        report = reportService.getReportManager();


        boolean isMerchantPayments = report.contains(payment);

        Assert.assertTrue(isMerchantPayments);

    }

    /**
     * @author Esben Damkjær Sørensen (s233474)
     */
    @And("a REPORT_GENERATED event should be published")
    public void aReportGeneratedEvent() {


        Event event = new Event(
                EventType.REPORT_GENERATED.getEventName(),
                new Object[]{
                        new ReportGenerated(
                                correlationId,
                                report
                        )

                }
        );

        verify(messageQueue).publish(event);

    }


}
