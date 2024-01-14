package dk.dtu.grp08.token.domain.events;

public enum EventType {

    PAYMENT_REQUESTED("PaymentRequested"),
    TOKEN_VALIDATED("TokenValidated"),
    TOKEN_INVALIDATED("TokenInvalidated");

    private String eventName;

    EventType(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
