package dk.dtu.grp08.manager.domain.policy;

import java.util.Map;
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

public class Policy<R> {

    private final Map<Class<?>, CompletableFuture<?>> dependencies;
    private final CompletableFuture<R> policy;

    private final Function<Policy<R>, R> policyFunction;

    protected Policy(
        ConcurrentHashMap<Class<?>, CompletableFuture<?>> dependencies,
        Function<Policy<R>, R> policyFunction
    ) {
        this.dependencies = dependencies;
        this.policyFunction = policyFunction;

        this.policy = CompletableFuture.allOf(
                    dependencies.values().toArray(new CompletableFuture[0])
                )
                    .thenApply((v) -> this)
                    .thenApply(
                        (p) -> {
                            try {
                                return this.policyFunction.apply(p);
                            } catch (Exception e) {
                                p.getCombinedFuture().completeExceptionally(e);
                                return null;
                            }
                        }
                    );
    }


    public <T> T getDependency(Class<?> eventType, Class<T> valueType) {
        return valueType.cast(this.dependencies.get(
            eventType
        ).join());
    }



    public CompletableFuture getDependency(Class<?> eventType) {
        return this.dependencies.get(eventType);
    }

    public CompletableFuture<R> getCombinedFuture() {
        return this.policy;
    }
}
