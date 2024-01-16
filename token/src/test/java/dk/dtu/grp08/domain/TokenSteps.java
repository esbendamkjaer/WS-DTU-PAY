package dk.dtu.grp08.domain;

import dk.dtu.grp08.token.data.repository.TokenRepository;
import dk.dtu.grp08.token.domain.events.EventType;
import dk.dtu.grp08.token.domain.events.PaymentRequestedEvent;
import dk.dtu.grp08.token.domain.events.TokenValidatedEvent;
import dk.dtu.grp08.token.domain.models.CorrelationId;
import dk.dtu.grp08.token.domain.models.Token;
import dk.dtu.grp08.token.domain.models.UserId;
import dk.dtu.grp08.token.domain.services.TokenService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import messaging.MessageQueue;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TokenSteps {

    private final MessageQueue messageQueue = mock(MessageQueue.class);
    private final TokenService tokenService = new TokenService(
        new TokenRepository(),
        messageQueue
    );

    private UserId userId;
    private Token token;
    private CorrelationId correlationId;


    @Given("a token")
    public void tokensStoredInTheService() {
        this.userId = new UserId(
            UUID.randomUUID()
        );

        this.token = tokenService
            .getTokens(1, userId)
            .removeLast();
    }

    @When("the PaymentRequestedEvent is received")
    public void thePaymentRequestedEventIsReceived() {
        this.correlationId = CorrelationId.randomId();

        this.tokenService.handlePaymentRequestedEvent(
            new messaging.Event(
                EventType.PAYMENT_REQUESTED.getEventName(),
                new Object[] {
                    new PaymentRequestedEvent(
                        UUID.randomUUID(),
                        this.token,
                        BigDecimal.valueOf(100),
                        this.correlationId
                    )
                }
            )
        );
    }

    @Then("the TokenValidatedEvent is sent")
    public void theTokenValidatedEventIsSent() {

        Event event = new Event(
            EventType.TOKEN_VALIDATED.getEventName(),
            new Object[] {
                new TokenValidatedEvent(
                    this.correlationId,
                    this.token,
                    this.userId
                )
            }
        );

        verify(this.messageQueue).publish(event);
    }

}
