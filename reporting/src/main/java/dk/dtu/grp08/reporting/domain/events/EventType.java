package dk.dtu.grp08.reporting.domain.events;

public enum EventType {

    PAYMENT_TRANSFERRED("PaymentTransferred"),

    CUSTOMER_REPORT_REQUESTED("CustomerReportRequested"),

    MERCHANT_REPORT_REQUESTED("MerchantReportRequested"),

    MANAGER_REPORT_REQUESTED("ManagerReportRequested");


    private String eventName;

    EventType(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
