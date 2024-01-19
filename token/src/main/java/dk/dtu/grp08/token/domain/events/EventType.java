package dk.dtu.grp08.token.domain.events;

/**
 * @author Fuad
 */
public enum EventType {

    PAYMENT_INITIATED("PaymentInitiated"),
    TOKEN_VALIDATED("TokenValidated"),
    TOKEN_INVALIDATED("TokenInvalidated"),
    TOKENS_REQUESTED("TokensRequested"),
    TOKENS_RETURNED("TokensReturned"),
    TOKEN_REQUEST_FAILED("TokenRequestFailed"),
    ;

    private final String eventName;

    /**
     * @author Fuad
     */
    EventType(String eventName) {
        this.eventName = eventName;
    }

    /**
     * @author Fuad
     */
    public String getEventName() {
        return eventName;
    }
}
