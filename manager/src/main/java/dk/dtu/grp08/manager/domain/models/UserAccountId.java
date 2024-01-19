package dk.dtu.grp08.manager.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;
/**
 * @author Alexander Matzen (s233475)
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountId {
    UUID id;
}
