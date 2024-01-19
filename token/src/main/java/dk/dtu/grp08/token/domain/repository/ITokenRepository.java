package dk.dtu.grp08.token.domain.repository;

import dk.dtu.grp08.token.domain.models.Token;
import dk.dtu.grp08.token.domain.models.UserId;

import java.util.List;
import java.util.Optional;

public interface ITokenRepository {

    /**
     * @author Fuad Hassan Jama (s233468)
     */
    Optional<UserId> getUserIdByToken(Token token);

    /**
     * @author Clair Norah Mutebi (s184187)
     */
    void saveToken(Token token, UserId userId);

    /**
     * @author Dilara Eda Celepli (s184262)
     */
    List<Token> getTokensByUserId(UserId userId);

    /**
     * @author Muhamad Hussein Nadali (s233479)
     */
    Optional<Token> deleteToken(Token token);

}
