package dk.dtu.grp08.reporting.domain.events;

public enum EventType {

    PAYMENT_TRANSFERRED("PaymentTransferred");

    private String eventName;

    EventType(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
