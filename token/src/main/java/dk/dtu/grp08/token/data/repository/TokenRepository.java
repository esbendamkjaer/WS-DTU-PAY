package dk.dtu.grp08.token.data.repository;

import dk.dtu.grp08.token.domain.models.Token;
import dk.dtu.grp08.token.domain.models.UserId;
import dk.dtu.grp08.token.domain.repository.ITokenRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class TokenRepository implements ITokenRepository {
    Map<UserId, List<Token>> userTokens = new ConcurrentHashMap<>();

    @Override
    public Optional<UserId> getUserIdByToken(Token token) {
        return userTokens
            .entrySet()
            .stream()
            .filter(entry -> entry.getValue().contains(token))
            .map(Map.Entry::getKey)
            .findFirst();
    }

    @Override
    public void saveToken(Token token, UserId userId) {
        userTokens
            .computeIfAbsent(userId, k -> new ArrayList<>())
            .add(token);
    }

    @Override
    public List<Token> getTokensByUserId(UserId userId) {
        return userTokens.getOrDefault(userId, new ArrayList<>());
    }

    @Override
    public Optional<Token> deleteToken(Token token) {
        Optional<UserId> userId = this.getUserIdByToken(token);

        if (userId.isEmpty()) {
            return Optional.empty();
        }

        List<Token> tokens = userTokens.get(
                userId.get()
        );

        tokens.remove(token);

        return Optional.of(token);
    }
}
