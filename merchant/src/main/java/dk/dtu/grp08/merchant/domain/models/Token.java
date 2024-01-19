package dk.dtu.grp08.merchant.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Dilara Eda Celepli (s184262)
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    UUID id;
}
