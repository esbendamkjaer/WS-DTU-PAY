package dk.dtu.grp08.customer.domain.models.events;

public enum EventType {



    REPORT_GENERATED("ReportGenerated"),
    CUSTOMER_REPORT_REQUESTED("CustomerReportRequested"),
    MERCHANT_REPORT_REQUESTED("MerchantReportRequested"),
    MANAGER_REPORT_REQUESTED("ManagerReportRequested")

    ;



    private String eventName;

    EventType(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
