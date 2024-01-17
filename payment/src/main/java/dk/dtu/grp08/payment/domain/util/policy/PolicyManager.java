package dk.dtu.grp08.payment.domain.util.policy;

import dk.dtu.grp08.payment.domain.models.CorrelationId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
            this.removePolicy(correlationId);
        });
    }

    public void removePolicy(CorrelationId correlationID) {
        this.correlations.remove(correlationID);
    }
}
