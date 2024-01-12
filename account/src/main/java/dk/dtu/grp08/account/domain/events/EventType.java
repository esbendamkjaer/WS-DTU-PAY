package dk.dtu.grp08.account.domain.events;

public enum EventType {

    TOKEN_VALIDATED("TokenValidated");

    private String eventName;

    EventType(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
