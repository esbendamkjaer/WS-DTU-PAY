package dk.dtu.grp08.token.domain.services;

import dk.dtu.grp08.token.domain.exceptions.InvalidTokenException;
import dk.dtu.grp08.token.domain.exceptions.TokenException;
import dk.dtu.grp08.token.domain.models.Token;
import dk.dtu.grp08.token.domain.models.UserId;
import dk.dtu.grp08.token.domain.repository.ITokenRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class TokenService implements ITokenService {

    @Inject
    ITokenRepository tokenRepository;

    private final int MAX_TOKENS_PER_USER = 6;

    @Override
    public List<Token> getTokens(int count, UserId userId) {
        List<Token> currentTokens = tokenRepository.getTokensByUserId(userId);

        if (
            currentTokens.size() > 1
        ) {
            throw new TokenException("User has more than one unused token");
        }

        if (currentTokens.size() + count > MAX_TOKENS_PER_USER) {
            throw new TokenException("User has too many unused tokens");
        }

        List<Token> tokens = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Token token = generateToken();
            tokenRepository.saveToken(token, userId);
            tokens.add(token);
        }

        return tokens;
    }

    @Override
    public void validateToken(Token token) {
        UserId userId = tokenRepository.getUserIdByToken(token);

        if (userId == null) {
            throw new TokenException("Token is invalid");
        }

        Optional<Token> deletedToken = this.tokenRepository
                .deleteToken(token);

        deletedToken.orElseThrow(InvalidTokenException::new);
    }

    private Token generateToken() {
        return new Token(
            UUID.randomUUID()
        );
    }
}
