package dk.dtu.grp08.manager.domain.events;

public enum EventType {

    MANAGER_REPORT_REQUESTED("ManagerReportRequested"),

    REPORT_GENERATED("ReportGenerated")
    ;

    private final String eventName;

    EventType(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
