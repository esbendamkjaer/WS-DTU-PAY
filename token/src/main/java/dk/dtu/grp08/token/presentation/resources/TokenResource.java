package dk.dtu.grp08.token.presentation.resources;

import dk.dtu.grp08.token.domain.models.Token;
import dk.dtu.grp08.token.domain.models.UserId;
import dk.dtu.grp08.token.domain.services.ITokenService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TokenResource implements ITokenResource {

    private final ITokenService tokenService;

    public TokenResource(ITokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public List<Token> getTokens(int count, UUID userId) {
        return this.tokenService.getTokens(
            count,
            new UserId(userId)
        );
    }
}
