package dk.dtu.grp08.customer.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountId {

    /**
     *
     * @author Muhamad Hussein Nadali (s233479)
     */

    UUID id;
}
