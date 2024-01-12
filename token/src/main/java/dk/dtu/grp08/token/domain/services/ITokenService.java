package dk.dtu.grp08.token.domain.services;

import dk.dtu.grp08.token.domain.exceptions.InvalidTokenException;
import dk.dtu.grp08.token.domain.models.Token;
import dk.dtu.grp08.token.domain.models.UserId;

import java.util.List;

public interface ITokenService {

    List<Token> getTokens(int count, UserId userId);

    UserId validateToken(Token token) throws InvalidTokenException;

}
