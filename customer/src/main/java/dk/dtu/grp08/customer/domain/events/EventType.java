package dk.dtu.grp08.customer.domain.events;

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
    ACCOUNT_REQUESTED("AccountRequested"),
    ACCOUNT_RETURNED("AccountReturned"),
    USER_NOT_FOUND("UserNotFound"),
    TOKENS_REQUESTED("TokensRequested"),
    TOKENS_RETURNED("TokensReturned"),
    TOKEN_REQUEST_FAILED("TokenRequestFailed"),
    REPORT_GENERATED("ReportGenerated"),
    CUSTOMER_REPORT_REQUESTED("CustomerReportRequested")
    ;

    private final String eventName;

    EventType(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
