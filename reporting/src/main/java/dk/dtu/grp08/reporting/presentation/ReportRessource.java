package dk.dtu.grp08.reporting.presentation;

import dk.dtu.grp08.reporting.domain.events.EventType;
import dk.dtu.grp08.reporting.domain.events.PaymentTransferEvent;
import dk.dtu.grp08.reporting.domain.models.Token;
import dk.dtu.grp08.reporting.domain.models.payment.Payment;
import dk.dtu.grp08.reporting.domain.services.IReportService;
import dk.dtu.grp08.reporting.presentation.contracts.IReportResource;
import jakarta.enterprise.context.ApplicationScoped;
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

    }


    public void handlePaymentTransferredEvent(Event event) {
        PaymentTransferEvent paymentTransferEvent = event.getArgument(0, PaymentTransferEvent.class);
       reportService.savePayment(paymentTransferEvent.getMerchantID(),paymentTransferEvent.getToken(),paymentTransferEvent.getAmount());


    }


    @Override
    public List<Payment> getReportCustomer(Token token) {
        return reportService.getReportCustomer(token);
    }


    @Override
    public List<Payment> getReportMerchant(UUID merchantID) {
        return reportService.getReportMerchant(merchantID);

    }

    @Override
    public List<Payment> getReportManager() {
       return reportService.getReportManager();

    }
}
