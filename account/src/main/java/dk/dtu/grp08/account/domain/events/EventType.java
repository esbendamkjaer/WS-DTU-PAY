package dk.dtu.grp08.account.domain.events;

/**
 * @author Esben Damkjær Sørensen (s233474)
 */
public enum EventType {

    PAYMENT_INITIATED("PaymentInitiated"),
    PAYMENT_TRANSFERRED("PaymentTransferred"),
    CUSTOMER_BANK_ACCOUNT_ASSIGNED("CustomerBankAccountAssigned"),
    MERCHANT_BANK_ACCOUNT_ASSIGNED("MerchantBankAccountAssigned"),
    TOKEN_INVALIDATED("TokenInvalidated"),
    ACCOUNT_REGISTRATION_REQUESTED("AccountRegistrationRequested"),
    ACCOUNT_REGISTERED("AccountRegistered"),
    ACCOUNT_DEREGISTRATION_REQUESTED("AccountDeregistrationRequested"),
    ACCOUNT_DEREGISTERED("AccountDeregistered"),
    TOKEN_VALIDATED("TokenValidated"),
    ACCOUNT_REQUESTED("AccountRequested"),
    ACCOUNT_RETURNED("AccountReturned"),
    USER_NOT_FOUND("UserNotFound");

    private final String eventName;

    EventType(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
