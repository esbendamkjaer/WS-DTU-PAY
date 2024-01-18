package dk.dtu.grp08.token.domain.events;

public enum EventType {

    PAYMENT_INITIATED("PaymentInitiated"),
    TOKEN_VALIDATED("TokenValidated"),
    TOKEN_INVALIDATED("TokenInvalidated"),
    TOKENS_REQUESTED("TokensRequested"),
    TOKENS_RETURNED("TokensReturned"),
    TOKEN_REQUEST_FAILED("TokenRequestFailed"),
    ;

    private final String eventName;

    EventType(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
