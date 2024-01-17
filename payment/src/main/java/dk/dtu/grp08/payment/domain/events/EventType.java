package dk.dtu.grp08.payment.domain.events;

public enum EventType {

    PAYMENT_REQUESTED("PaymentRequested"),
    PAYMENT_TRANSFERRED("PaymentTransferred"),
    CUSTOMER_BANK_ACCOUNT_ASSIGNED("CustomerBankAccountAssigned"),
    MERCHANT_BANK_ACCOUNT_ASSIGNED("MerchantBankAccountAssigned"),
    TOKEN_INVALIDATED("TokenInvalidated"),
    PAYMENT_CANCELED("PaymentCanceled")
    ;

    private String eventName;

    EventType(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
