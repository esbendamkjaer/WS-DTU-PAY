package dk.dtu.grp08.token.presentation.resources;

import dk.dtu.grp08.token.domain.events.EventType;
import dk.dtu.grp08.token.domain.events.PaymentRequestedEvent;
import dk.dtu.grp08.token.domain.events.TokenInvalidatedEvent;
import dk.dtu.grp08.token.domain.events.TokenValidatedEvent;
import dk.dtu.grp08.token.domain.exceptions.InvalidTokenException;
import dk.dtu.grp08.token.domain.models.Token;
import dk.dtu.grp08.token.domain.models.UserId;
import dk.dtu.grp08.token.domain.services.ITokenService;
import jakarta.enterprise.context.ApplicationScoped;
import messaging.Event;
import messaging.MessageQueue;
import messaging.implementations.RabbitMqQueue;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TokenResource implements ITokenResource {

    private final MessageQueue messageQueue;

    private final ITokenService tokenService;

    public TokenResource(ITokenService tokenService) {
        this.tokenService = tokenService;

        this.messageQueue = new RabbitMqQueue("localhost");

        this.messageQueue.addHandler(
            EventType.PAYMENT_REQUESTED.getEventName(),
            this::handlePaymentRequestedEvent
        );
    }

    @Override
    public List<Token> getTokens(int count, UUID userId) {
        return this.tokenService.getTokens(
            count,
            new UserId(userId)
        );
    }

    public void handlePaymentRequestedEvent(Event event) {
        PaymentRequestedEvent paymentRequestedEvent = event.getArgument(0, PaymentRequestedEvent.class);

        UserId userId;

        try {
            userId = this.tokenService.validateToken(
                paymentRequestedEvent.getToken()
            );
        } catch (InvalidTokenException e) {
            Event invalidTokenEvent = new Event(
                    EventType.TOKEN_INVALIDATED.getEventName(),
                    new Object[] {
                        new TokenInvalidatedEvent(
                            paymentRequestedEvent.getCorrelationId(),
                            paymentRequestedEvent.getToken()
                        )
                    }
            );

            this.messageQueue.publish(invalidTokenEvent);

            return;
        }

        Event validTokenEvent = new Event(
            EventType.TOKEN_VALIDATED.getEventName(),
            new Object[] {
                new TokenValidatedEvent(
                    paymentRequestedEvent.getCorrelationId(),
                    paymentRequestedEvent.getToken(),
                    userId
                )
            }
        );

        messageQueue.publish(validTokenEvent);
    }

}
