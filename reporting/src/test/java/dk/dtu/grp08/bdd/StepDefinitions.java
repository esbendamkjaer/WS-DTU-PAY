package dk.dtu.grp08.bdd;


import dk.dtu.grp08.reporting.data.repositories.ReportRepository;
import dk.dtu.grp08.reporting.domain.events.EventType;
import dk.dtu.grp08.reporting.domain.events.PaymentTransferEvent;
import dk.dtu.grp08.reporting.domain.models.CorrelationId;
import dk.dtu.grp08.reporting.domain.models.Token;
import dk.dtu.grp08.reporting.domain.models.user.UserAccount;
import dk.dtu.grp08.reporting.domain.services.ReportService;
import dk.dtu.grp08.reporting.presentation.ReportRessource;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import messaging.MessageQueue;
import org.junit.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
    private PaymentTransferEvent paymentTransferredEvent;


    EventType eventType = EventType.PAYMENT_TRANSFERRED;



    @When("a PAYMENT_TRANSFERRED event is received")
    public void aPaymentTransferredEvent() {
        this.token = new Token(
                UUID.randomUUID()
        );

        paymentTransferredEvent = new PaymentTransferEvent(
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


        //Assert.assertTrue();
    }





}
