package dk.dtu.grp08.merchant.domain.events;

public enum EventType {
    /**
     * @author Fuad Hassan Jama (s233468)
     */

    PAYMENT_INITIATED("PaymentInitiated"),
    PAYMENT_TRANSFERRED("PaymentTransferred"),
    CUSTOMER_BANK_ACCOUNT_ASSIGNED("CustomerBankAccountAssigned"),
    MERCHANT_BANK_ACCOUNT_ASSIGNED("MerchantBankAccountAssigned"),
    TOKEN_INVALIDATED("TokenInvalidated"),
    ACCOUNT_REGISTRATION_REQUESTED("AccountRegistrationRequested"),
    ACCOUNT_REGISTERED("AccountRegistered"),
    ACCOUNT_DEREGISTRATION_REQUESTED("AccountDeregistrationRequested"),
    ACCOUNT_DEREGISTERED("AccountDeregistered"),
    USER_NOT_FOUND("UserNotFound"),
    PAYMENT_REQUESTED("PaymentRequested"),
    PAYMENT_FAILED("PaymentFailed"),
    REPORT_GENERATED("ReportGenerated"),
    CUSTOMER_REPORT_REQUESTED("CustomerReportRequested"),
    MERCHANT_REPORT_REQUESTED("MerchantReportRequested"),
    MANAGER_REPORT_REQUESTED("ManagerReportRequested")

    ;

    private final String eventName;

    EventType(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
