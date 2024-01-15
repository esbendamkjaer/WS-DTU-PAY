package dk.dtu.grp08.payment.domain.services;

import dk.dtu.grp08.payment.domain.events.EventType;
import dk.dtu.grp08.payment.domain.models.CorrelationId;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class PolicyManager {

    Map<CorrelationId, Map<EventType, CompletableFuture>> correlation = new ConcurrentHashMap<>();

    public <T> CompletableFuture<T> getPolicyByCorrelationIdAndEvent(CorrelationId correlationId, EventType eventType, Class<T> type) {
        return this.correlation.get(correlationId).get(eventType);
    }

    public <T> void addPolicy(CorrelationId correlationId, Map<EventType, CompletableFuture> policy) {
        this.correlation.put(correlationId, policy);
    }

    public void removePolicy(CorrelationId correlationID) {
        this.correlation.remove(correlationID);
    }
}
