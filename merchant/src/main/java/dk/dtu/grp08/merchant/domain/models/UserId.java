package dk.dtu.grp08.merchant.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
/**
 * @author Esben Damkjær Sørensen (s233474)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserId {
    UUID id;
}
