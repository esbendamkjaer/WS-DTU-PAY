package dk.dtu.grp08.payment.domain.events;

/**
 * @author Dilara
 */
public enum EventType {

    PAYMENT_INITIATED("PaymentInitiated"),
    PAYMENT_TRANSFERRED("PaymentTransferred"),
    CUSTOMER_BANK_ACCOUNT_ASSIGNED("CustomerBankAccountAssigned"),
    MERCHANT_BANK_ACCOUNT_ASSIGNED("MerchantBankAccountAssigned"),
    TOKEN_INVALIDATED("TokenInvalidated"),
    PAYMENT_REQUESTED("PaymentRequested"),
    PAYMENT_FAILED("PaymentFailed")
    ;

    private String eventName;

    EventType(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
