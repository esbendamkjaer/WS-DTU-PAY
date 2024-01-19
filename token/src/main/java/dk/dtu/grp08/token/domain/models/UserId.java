package dk.dtu.grp08.token.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.UUID;

/**
 * @author Muhamad Hussein Nadali (s233479)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserId {
    UUID id;
}
