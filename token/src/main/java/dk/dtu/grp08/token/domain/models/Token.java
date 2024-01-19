package dk.dtu.grp08.token.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.UUID;

/**
 * @author Dilara
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    UUID id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token token)) return false;
        return id.equals(token.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
