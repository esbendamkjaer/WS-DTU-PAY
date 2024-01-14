package dk.dtu.grp08.payment.domain.services;

import dk.dtu.grp08.payment.domain.events.EventType;
import dk.dtu.grp08.payment.domain.models.CorrelationId;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class  PolicyManager {

    Map<CorrelationId, Map<EventType, CompletableFuture>> correlation = new ConcurrentHashMap<>();

    public  CompletableFuture<? extends IPolicy> getPolicyByCorrelationIdAndEvent(CorrelationId correlationId, EventType eventType) {
        return this.correlation.get(correlationId).get(eventType);
    }

    public void addPolicy(CorrelationId correlationId, Map<EventType, CompletableFuture> policy) {
        this.correlation.put(correlationId,policy);
    }


    public <T> void removePolicy(CorrelationId correlationId) {
        this.correlation.remove(correlationId);
    }

}
