package dk.dtu.grp08.token.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.UUID;

/**
 * @author Muhamad
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserId {
    UUID id;
}
