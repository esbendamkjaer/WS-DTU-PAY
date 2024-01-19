package dk.dtu.grp08.customer.domain.services;

import dk.dtu.grp08.customer.domain.events.EventType;
import dk.dtu.grp08.customer.domain.events.TokenRequestFailed;
import dk.dtu.grp08.customer.domain.events.TokensRequestedEvent;
import dk.dtu.grp08.customer.domain.events.TokensReturnedEvent;
import dk.dtu.grp08.customer.domain.exceptions.TokenException;
import dk.dtu.grp08.customer.domain.models.CorrelationId;
import dk.dtu.grp08.customer.domain.models.Token;
import dk.dtu.grp08.customer.domain.models.UserAccountId;
import dk.dtu.grp08.customer.domain.policy.Policy;
import dk.dtu.grp08.customer.domain.policy.PolicyBuilder;
import dk.dtu.grp08.customer.domain.policy.PolicyManager;
import dk.dtu.grp08.customer.domain.services.contracts.ITokenService;
import jakarta.enterprise.context.ApplicationScoped;
import messaging.Event;
import messaging.MessageQueue;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class TokenService implements ITokenService {

    private final MessageQueue messageQueue;
    private final PolicyManager policyManager;

    /**
     *
     * @author Muhamad Hussein Nadali (s233479)
     */
    public TokenService(
        MessageQueue messageQueue,
        PolicyManager policyManager
    ) {
        this.messageQueue = messageQueue;
        this.policyManager = policyManager;

        this.messageQueue.addHandler(
            EventType.TOKENS_RETURNED.getEventName(),
            this::handleTokensReturnedEvent
        );

        this.messageQueue.addHandler(
            EventType.TOKEN_REQUEST_FAILED.getEventName(),
            this::handleTokenRequestFailedEvent
        );
    }

    /**
     *
     * @author Fuad Hassan Jama (s233468)
     */
    @Override
    public CompletableFuture<List<Token>> getTokens(int count, UserAccountId userId) {
        CorrelationId correlationId = CorrelationId.randomId();

        Policy<List<Token>> tokenPolicy = new PolicyBuilder<List<Token>>()
            .addPart(
                TokensReturnedEvent.class
            )
            .setPolicyFunction(
                (p) -> p.getDependency(
                    TokensReturnedEvent.class,
                    List.class
                )
            ).build();

        this.policyManager.addPolicy(
            correlationId,
            tokenPolicy
        );

        Event tokensRequestedEvent = new Event(
            EventType.TOKENS_REQUESTED.getEventName(),
            new Object[]{
                new TokensRequestedEvent(
                    correlationId,
                    userId,
                    count
                )
            }
        );

        this.messageQueue.publish(
            tokensRequestedEvent
        );

        return tokenPolicy.getCombinedFuture();
    }

    /**
     *
     * @author Esben Damkjær Sørensen (s233474)
     */
    public void handleTokensReturnedEvent(Event event) {
        TokensReturnedEvent tokensReturnedEvent = event.getArgument(0, TokensReturnedEvent.class);

        if (!this.policyManager.hasPolicy(tokensReturnedEvent.getCorrelationId())) {
            return;
        }

        this.policyManager.getPolicy(
            tokensReturnedEvent.getCorrelationId()
        ).getDependency(
            TokensReturnedEvent.class
        ).complete(
            tokensReturnedEvent.getTokens()
        );
    }

    /**
     *
     * @author Dilara Eda Celepli (s184262)
     */
    public void handleTokenRequestFailedEvent(Event event) {
        TokenRequestFailed tokenRequestFailed = (TokenRequestFailed) event.getArgument(0, TokenRequestFailed.class);

        if (!this.policyManager.hasPolicy(tokenRequestFailed.getCorrelationId())) {
            return;
        }

        this.policyManager.getPolicy(
            tokenRequestFailed.getCorrelationId()
        ).getCombinedFuture()
            .completeExceptionally(
                new TokenException(tokenRequestFailed.getCause())
            );
    }
}
