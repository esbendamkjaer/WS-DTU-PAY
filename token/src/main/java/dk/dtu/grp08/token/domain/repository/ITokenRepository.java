package dk.dtu.grp08.token.domain.repository;

import dk.dtu.grp08.token.domain.models.Token;
import dk.dtu.grp08.token.domain.models.UserId;

import java.util.List;
import java.util.Optional;

public interface ITokenRepository {

    /**
     * @author Fuad
     */
    Optional<UserId> getUserIdByToken(Token token);

    /**
     * @author Clair
     */
    void saveToken(Token token, UserId userId);

    /**
     * @author Dilara
     */
    List<Token> getTokensByUserId(UserId userId);

    /**
     * @author Muhamad
     */
    Optional<Token> deleteToken(Token token);

}
