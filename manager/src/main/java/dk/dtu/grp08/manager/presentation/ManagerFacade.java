package dk.dtu.grp08.manager.presentation;

import dk.dtu.grp08.manager.domain.events.EventType;
import dk.dtu.grp08.manager.domain.events.ManagerReportRequested;
import dk.dtu.grp08.manager.domain.events.ReportGenerated;
import dk.dtu.grp08.manager.domain.models.CorrelationId;
import dk.dtu.grp08.manager.domain.models.Payment;
import dk.dtu.grp08.manager.domain.models.UserAccountId;
import dk.dtu.grp08.manager.domain.policy.Policy;
import dk.dtu.grp08.manager.domain.policy.PolicyBuilder;
import dk.dtu.grp08.manager.domain.policy.PolicyManager;
import dk.dtu.grp08.manager.presentation.contracts.IManagerFacade;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.val;
import messaging.Event;
import messaging.MessageQueue;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;


@ApplicationScoped
public class ManagerFacade implements IManagerFacade {

    private final MessageQueue messageQueue;

    private final PolicyManager policyManager;


    public ManagerFacade(
                            MessageQueue messageQueue) {
        this.messageQueue = messageQueue;

        policyManager = new PolicyManager();


        this.messageQueue.addHandler(
                EventType.REPORT_GENERATED.getEventName(),
                this::handleReportGenerated
        );

    }



    @Override
    public CompletableFuture<List<Payment>> getReport() {
        CorrelationId correlationID = CorrelationId.randomId();

        Policy<List<Payment>> policy = new PolicyBuilder<List<Payment>>()
                .addPart(ReportGenerated.class)
                .setPolicyFunction(
                        (p) -> {
                            List<Payment> report = p.getDependency(ReportGenerated.class, List.class);

                            return report;
                        }
                ).build();

        policyManager.addPolicy(correlationID, policy);

        messageQueue.publish(
                new Event(
                        EventType.MANAGER_REPORT_REQUESTED.getEventName(),
                        new Object[]{
                                new ManagerReportRequested(
                                        correlationID
                                )
                        }
                )
        );

        return policy.getCombinedFuture();
    }


    public void handleReportGenerated(Event mqEvent) {
        val event = mqEvent.getArgument(0, ReportGenerated.class);

        if (!this.policyManager.hasPolicy(event.getCorrelationId())) {
            return;
        }


        CompletableFuture<List<Payment>> future = policyManager.getPolicy(
                event.getCorrelationId()
        ).getDependency(
                ReportGenerated.class
        );

        future.complete(event.getReport());
    }
}