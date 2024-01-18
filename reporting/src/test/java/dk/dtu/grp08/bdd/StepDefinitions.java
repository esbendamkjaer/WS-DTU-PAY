package dk.dtu.grp08.bdd;


import dk.dtu.grp08.reporting.data.repositories.ReportRepository;
import dk.dtu.grp08.reporting.domain.events.CustomerReportRequested;
import dk.dtu.grp08.reporting.domain.events.EventType;
import dk.dtu.grp08.reporting.domain.events.PaymentTransferredEvent;
import dk.dtu.grp08.reporting.domain.models.CorrelationId;
import dk.dtu.grp08.reporting.domain.models.Token;
import dk.dtu.grp08.reporting.domain.models.payment.Payment;
import dk.dtu.grp08.reporting.domain.models.user.UserAccount;
import dk.dtu.grp08.reporting.domain.services.ReportService;
import dk.dtu.grp08.reporting.presentation.ReportRessource;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.MessageQueue;
import org.junit.Assert;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import static org.mockito.Mockito.mock;

public class StepDefinitions {

    private final MessageQueue messageQueue = mock(MessageQueue.class);

    private ReportRepository reportRepository = new ReportRepository();

    public ReportRessource reportRessource = new ReportRessource(
            new ReportService(reportRepository),
            messageQueue
    );



    private CorrelationId correlationId;

    private Token token;

    private UserAccount customer;
    private UserAccount merchant;
    private PaymentTransferredEvent paymentTransferredEvent;

    private CustomerReportRequested customerReportRequestedEvent;


    EventType eventType = EventType.PAYMENT_TRANSFERRED;



    @When("a PAYMENT_TRANSFERRED event is received")
    public void aPaymentTransferredEvent() {
        this.token = new Token(
                UUID.randomUUID()
        );

        paymentTransferredEvent = new PaymentTransferredEvent(
                UUID.randomUUID(),
                this.token,
                BigDecimal.valueOf(100)
        );


        this.correlationId = CorrelationId.randomId();

        this.reportRessource.handlePaymentTransferredEvent(
                new messaging.Event(
                        EventType.PAYMENT_TRANSFERRED.getEventName(),
                        new Object[] {
                                paymentTransferredEvent

                        }
                )
        );
    }


    @Then("the payment should be saved")
    public void thePaymentShouldBeSaved() {
        System.out.println("reportRepository.getPayments() = " + reportRepository.getPayments());

        Payment recordedPayment = reportRepository.getPayments().get(0);

        Assert.assertTrue(paymentTransferredEvent.getMerchantID().equals(recordedPayment.getCreditor()) &&
                recordedPayment.getDebtor().getId().equals(paymentTransferredEvent.getToken().getId())  &&
                Objects.equals(recordedPayment.getAmount(), paymentTransferredEvent.getAmount()));
    }


    @When("a CUSTOMER_REPORT_REQUESTED event is received")
    public void aCustomerReportRequestedEvent() {
        this.token = new Token(
                UUID.randomUUID()
        );

        customerReportRequestedEvent = new CustomerReportRequested();

        this.correlationId = CorrelationId.randomId();

        this.reportRessource.handlePaymentTransferredEvent(
                new messaging.Event(
                        EventType.PAYMENT_TRANSFERRED.getEventName(),
                        new Object[] {
                                paymentTransferredEvent

                        }
                )
        );
    }







}
