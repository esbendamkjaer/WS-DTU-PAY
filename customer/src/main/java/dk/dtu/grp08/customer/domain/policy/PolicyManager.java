package dk.dtu.grp08.customer.domain.policy;


import dk.dtu.grp08.customer.domain.models.CorrelationId;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped

public class  PolicyManager {

    Map<CorrelationId, Policy<?>> correlations = new ConcurrentHashMap<>();

    public Policy<?> getPolicy(
            CorrelationId correlationId
    ) {
        return this.correlations.get(correlationId);
    }

    public void addPolicy(CorrelationId correlationId, Policy<?> policy) {
        this.correlations.put(correlationId, policy);
        policy.getCombinedFuture().thenAccept((p) -> {
            this.correlations.remove(correlationId);
        });
    }

    public void removePolicy(CorrelationId correlationID) {
        this.correlations.remove(correlationID);
    }

    public boolean hasPolicy(CorrelationId correlationId) {
        return this.correlations.containsKey(correlationId);
    }
}