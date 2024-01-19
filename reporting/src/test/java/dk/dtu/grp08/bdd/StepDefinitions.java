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


    @Given("a Customer with id {string}")
    public void setCustomer(String customerID) {
        this.customerID = new UserAccountId(UUID.fromString(customerID));

    }


    @And("a Merchant with id {string}")
    public void setMerchant( String merchantID) {
        this.merchantID = new UserAccountId(UUID.fromString(merchantID));


    }

    @And("a Payment with {int} kr")
    public void setPayment(int amount) {
        this.payment = new Payment();
        this.payment.setAmount(BigDecimal.valueOf(amount));
        this.payment.setCreditor(this.merchantID);
        this.payment.setDebtor(this.customerID);

    }




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



    @Then("the payment should be saved")
    public void thePaymentShouldBeSaved() {


        Payment paymentSaved = reportRepository.getPayments().get(0);



        Assert.assertTrue(paymentSaved.getCreditor().getId().equals(payment.getCreditor().getId()) &&
                payment.getDebtor().getId().equals(paymentSaved.getDebtor().getId()) &&
                Objects.equals(payment.getAmount(), paymentSaved.getAmount()));
    }


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




    @Then("a report should be generated with all payments for the customer")
    public void aReportGeneratedCustomer() {


        report = reportService.getReportCustomer(customerID);
        boolean isCustomerPayments = report.stream().allMatch(payment -> payment.getDebtor().getId().equals(customerID.getId()));

        Assert.assertTrue(isCustomerPayments);

    }

    @Then("a report should be generated with all payments to the merchant")
    public void aReportGeneratedMerchant() {


        report = reportService.getReportMerchant(merchantID);
        boolean isMerchantPayments = report.stream().allMatch(payment -> payment.getCreditor().getId().equals(merchantID.getId()));

        Assert.assertTrue(isMerchantPayments);

    }


    @Then("a report should be generated with all payments")
    public void aReportGeneratedManager() {


        report = reportService.getReportManager();


        boolean isMerchantPayments = report.contains(payment);

        Assert.assertTrue(isMerchantPayments);

    }


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
