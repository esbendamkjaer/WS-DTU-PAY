package dk.dtu.grp08.token.domain.services;

import dk.dtu.grp08.token.domain.events.EventType;
import dk.dtu.grp08.token.domain.events.PaymentRequestedEvent;
import dk.dtu.grp08.token.domain.events.TokenInvalidatedEvent;
import dk.dtu.grp08.token.domain.events.TokenValidatedEvent;
import dk.dtu.grp08.token.domain.exceptions.InvalidTokenException;
import dk.dtu.grp08.token.domain.exceptions.TokenException;
import dk.dtu.grp08.token.domain.models.Token;
import dk.dtu.grp08.token.domain.models.UserId;
import dk.dtu.grp08.token.domain.repository.ITokenRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import messaging.Event;
import messaging.MessageQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class TokenService implements ITokenService {

    private final ITokenRepository tokenRepository;
    private final MessageQueue messageQueue;

    private final int MAX_TOKENS_PER_USER = 6;

    public TokenService(
        ITokenRepository tokenRepository,
        MessageQueue messageQueue
    ) {
        this.tokenRepository = tokenRepository;
        this.messageQueue = messageQueue;
        
        this.messageQueue.addHandler(
            EventType.PAYMENT_REQUESTED.getEventName(),
            this::handlePaymentRequestedEvent
        );
    }

    @Override
    public List<Token> getTokens(int count, UserId userId) {
        List<Token> currentTokens = tokenRepository.getTokensByUserId(userId);

        if (count < 1) {
            throw new TokenException("Illegal number of tokens requested");
        }

        if (
            currentTokens.size() > 1
        ) {
            throw new TokenException("User has more than one unused token");
        }

        if (currentTokens.size() + count > MAX_TOKENS_PER_USER) {
            throw new TokenException("Token amount exceeds allowed limit");
        }

        List<Token> tokens = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Token token = generateToken();
            tokenRepository.saveToken(token, userId);
            tokens.add(token);
        }

        return tokens;
    }

    @Override
    public UserId validateToken(Token token) throws InvalidTokenException {
        UserId userId = this.tokenRepository.getUserIdByToken(token)
                .orElseThrow(InvalidTokenException::new);

        Optional<Token> deletedToken = this.tokenRepository
                .deleteToken(token);

        deletedToken.orElseThrow(InvalidTokenException::new);

        return userId;
    }

    private Token generateToken() {
        return new Token(
            UUID.randomUUID()
        );
    }

    public void handlePaymentRequestedEvent(Event event) {
        PaymentRequestedEvent paymentRequestedEvent = event.getArgument(0, PaymentRequestedEvent.class);

        UserId userId;

        try {
            userId = this.validateToken(
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
