package dk.dtu.grp08.dtupay.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

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
