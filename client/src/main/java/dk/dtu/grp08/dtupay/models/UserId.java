package dk.dtu.grp08.dtupay.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.UUID;

/**
 * @author Alexander Matzen (s233475)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserId {
    UUID id;
}
