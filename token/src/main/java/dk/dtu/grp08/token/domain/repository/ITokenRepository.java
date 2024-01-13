package dk.dtu.grp08.token.domain.repository;

import dk.dtu.grp08.token.domain.models.Token;
import dk.dtu.grp08.token.domain.models.UserId;

import java.util.List;
import java.util.Optional;

public interface ITokenRepository {

    Optional<UserId> getUserIdByToken(Token token);

    void saveToken(Token token, UserId userId);

    List<Token> getTokensByUserId(UserId userId);

    Optional<Token> deleteToken(Token token);

}
