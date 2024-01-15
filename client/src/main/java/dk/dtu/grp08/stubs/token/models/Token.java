package dk.dtu.grp08.stubs.token.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    UUID id;
}
