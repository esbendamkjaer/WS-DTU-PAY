package dk.dtu.grp08.manager.domain.policy;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @author Clair Norah Mutebi (s184187)
 * @author Muhamad Hussein Nadali (s233479)
 * @author Dilara Eda Celepli (s184262)
 * @author Fuad Hassan Jama (s233468)
 * @author Esben Damkjær Sørensen (s233474)
 * @author Alexander Matzen (s233475)
 */

public class PolicyBuilder<R> {

    private final Map<Class<?>, CompletableFuture<?>> partials = new ConcurrentHashMap<>();
    private Optional<Function<Policy<R>, R>> policyFunction = Optional.empty();

    public PolicyBuilder<R> addPart(Class<?> eventType) {
        return this.addPart(
            eventType,
            new CompletableFuture<>()
        );
    }


    public PolicyBuilder<R> addPart(Class<?> eventType, CompletableFuture<?> part) {
        this.partials.put(eventType, part);
        return this;
    }


    public PolicyBuilder<R> setPolicyFunction(Function<Policy<R>, R> policyFunction) {
        this.policyFunction = Optional.of(policyFunction);
        return this;
    }

    public Policy<R> build() {
        return new Policy<>(
            new ConcurrentHashMap<>(this.partials),
            this.policyFunction.orElse(
                (p) -> null
            )
        );
    }

}
