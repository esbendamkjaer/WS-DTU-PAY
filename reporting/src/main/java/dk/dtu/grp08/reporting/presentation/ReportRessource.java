package dk.dtu.grp08.reporting.presentation;

import dk.dtu.grp08.reporting.domain.events.CustomerReportRequested;
import dk.dtu.grp08.reporting.domain.events.EventType;
import dk.dtu.grp08.reporting.domain.events.MerchantReportRequested;
import dk.dtu.grp08.reporting.domain.events.PaymentTransferEvent;
import dk.dtu.grp08.reporting.domain.models.Token;
import dk.dtu.grp08.reporting.domain.models.payment.Payment;
import dk.dtu.grp08.reporting.domain.services.IReportService;
import dk.dtu.grp08.reporting.presentation.contracts.IReportResource;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.val;
import messaging.Event;
import messaging.MessageQueue;
import messaging.implementations.RabbitMqQueue;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ReportRessource implements IReportResource {

    IReportService reportService;

    private final MessageQueue messageQueue;

    public ReportRessource(
            IReportService reportService,
            MessageQueue messageQueue
    ) {
        this.reportService = reportService;

        this.messageQueue = messageQueue;

        this.messageQueue.addHandler(
                EventType.PAYMENT_TRANSFERRED.getEventName(),
                this::handlePaymentTransferredEvent
        );
        this.messageQueue.addHandler(
                EventType.CUSTOMER_REPORT_REQUESTED.getEventName(),
                this::handleCustomerReportRequested
        );
        this.messageQueue.addHandler(
                EventType.MERCHANT_REPORT_REQUESTED.getEventName(),
                this::handleMerchantReportRequested
        );
        this.messageQueue.addHandler(
                EventType.MANAGER_REPORT_REQUESTED.getEventName(),
                this::handleManagerReportRequested
        );

    }


    public void handlePaymentTransferredEvent(Event event) {
        PaymentTransferEvent paymentTransferEvent = event.getArgument(0, PaymentTransferEvent.class);
        reportService.savePayment(paymentTransferEvent.getMerchantID(), paymentTransferEvent.getToken(), paymentTransferEvent.getAmount());


    }


    public void handleCustomerReportRequested(Event e) {
        val event = e.getArgument(0, CustomerReportRequested.class);
        reportService.getReportCustomer(event.getToken());

        messageQueue.publish(
                new Event(
                        EventType.CUSTOMER_REPORT_REQUESTED.getEventName(),
                        new Object[]{
                                reportService.getReportCustomer(event.getToken())

                        }
                ));
    }


    public void handleMerchantReportRequested(Event e) {

        val event = e.getArgument(0, MerchantReportRequested.class);

        messageQueue.publish(
                new Event(
                        EventType.MERCHANT_REPORT_REQUESTED.getEventName(),
                        new Object[]{
                                reportService.getReportMerchant(event.getId())

                        }
                ));

    }

    public void handleManagerReportRequested(Event e) {
        messageQueue.publish(
                new Event(
                        EventType.MANAGER_REPORT_REQUESTED.getEventName(),
                        new Object[]{
                                reportService.getReportManager()
                        }
                ));

    }
}
