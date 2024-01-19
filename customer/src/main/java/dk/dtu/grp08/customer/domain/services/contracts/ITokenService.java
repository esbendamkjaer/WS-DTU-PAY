package dk.dtu.grp08.customer.domain.services.contracts;

import dk.dtu.grp08.customer.domain.models.Token;
import dk.dtu.grp08.customer.domain.models.UserAccountId;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ITokenService {
    /**
     *
     * @author Fuad
     */
    CompletableFuture<List<Token>> getTokens(int count, UserAccountId userId);
}
