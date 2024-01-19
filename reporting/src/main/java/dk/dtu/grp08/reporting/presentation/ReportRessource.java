package dk.dtu.grp08.reporting.presentation;

import dk.dtu.grp08.reporting.domain.events.*;
import dk.dtu.grp08.reporting.domain.models.Token;
import dk.dtu.grp08.reporting.domain.models.payment.Payment;
import dk.dtu.grp08.reporting.domain.services.IReportService;
import dk.dtu.grp08.reporting.presentation.contracts.IReportResource;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.val;
import messaging.Event;
import messaging.MessageQueue;
import messaging.implementations.RabbitMqQueue;

import java.util.List;
import java.util.UUID;


@Startup
@ApplicationScoped
public class ReportRessource implements IReportResource {

    IReportService reportService;

    private final MessageQueue messageQueue;
    /**
     * @author Muhamad Hussein Nadali (s233479)
     */
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

    /**
     * @author Clair Norah Mutebi (s184187)
     */

    public void handlePaymentTransferredEvent(Event event) {
        PaymentTransferredEvent paymentTransferEvent = event.getArgument(0, PaymentTransferredEvent.class);
        reportService.savePayment(paymentTransferEvent.getMerchantId(), paymentTransferEvent.getCustomerId(), paymentTransferEvent.getPayment().getAmount());

    }

    /**
     * @author Clair Norah Mutebi (s184187)
     */

    public void handleCustomerReportRequested(Event e) {
        val event = e.getArgument(0, CustomerReportRequested.class);



        messageQueue.publish(
                new Event(
                        EventType.REPORT_GENERATED.getEventName(),
                        new Object[]{
                                new ReportGenerated(
                                        event.getCorrelationId(),
                                        reportService.getReportCustomer(event.getCustomerID())
                                )



                        }
                ));
    }

    /**
     * @author Dilara Eda Celepli (s184262)
     */
    public void handleMerchantReportRequested(Event e) {

        val event = e.getArgument(0, MerchantReportRequested.class);


        messageQueue.publish(
                new Event(
                        EventType.REPORT_GENERATED.getEventName(),
                        new Object[]{
                                new ReportGenerated(
                                        event.getCorrelationId(),
                                        reportService.getReportMerchant(event.getMerchantID())
                                )



                        }
                ));

    }
    /**
     * @author Dilara Eda Celepli (s184262)
     */
    public void handleManagerReportRequested(Event e) {
        val event = e.getArgument(0, ManagerReportRequested.class);


        messageQueue.publish(
                new Event(
                        EventType.REPORT_GENERATED.getEventName(),
                        new Object[]{
                                new ReportGenerated(
                                        event.getCorrelationId(),
                                        reportService.getReportManager()
                                )



                        }
                ));

    }
}
