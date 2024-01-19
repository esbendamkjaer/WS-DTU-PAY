package dk.dtu.grp08.reporting.domain.events;

/**
 * @author Dilara Eda Celepli (s184262)
 */
import dk.dtu.grp08.reporting.domain.models.CorrelationId;
import dk.dtu.grp08.reporting.domain.models.Token;
import dk.dtu.grp08.reporting.domain.models.user.UserAccountId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantReportRequested {
    UserAccountId merchantID;
    CorrelationId correlationId;

}
