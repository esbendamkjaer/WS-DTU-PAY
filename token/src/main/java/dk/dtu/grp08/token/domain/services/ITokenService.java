package dk.dtu.grp08.token.domain.services;

import dk.dtu.grp08.token.domain.exceptions.InvalidTokenException;
import dk.dtu.grp08.token.domain.models.Token;
import dk.dtu.grp08.token.domain.models.UserId;

import java.util.List;

public interface ITokenService {

    /**
     * @author Alexander Matzen (s233475)
     */
    List<Token> getTokens(int count, UserId userId);

    /**
     * @author Esben Damkjær Sørensen (s233474)
     */
    UserId validateToken(Token token) throws InvalidTokenException;

}
