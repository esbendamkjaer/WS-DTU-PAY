package dk.dtu.grp08.reporting.domain.events;


import dk.dtu.grp08.reporting.domain.models.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerReportRequested {
    Token token;

}
