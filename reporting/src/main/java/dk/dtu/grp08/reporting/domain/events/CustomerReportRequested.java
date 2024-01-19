package dk.dtu.grp08.reporting.domain.events;


import dk.dtu.grp08.reporting.domain.models.CorrelationId;
import dk.dtu.grp08.reporting.domain.models.Token;
import dk.dtu.grp08.reporting.domain.models.user.UserAccountId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;
/**
 * @author Alexander Matzen (s233475)
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerReportRequested {
    UserAccountId customerID;
    CorrelationId correlationId;

}
