package dk.dtu.grp08.account.domain.events;

public enum EventType {

    TOKEN_VALIDATED("TokenValidated"),
    TOKEN_INVALIDATED("TokenInvalidated"),
    CUSTOMER_BANK_ACCOUNT_NO_ASSIGNED("CustomerBankAccountNoAssigned");

    private String eventName;

    EventType(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
