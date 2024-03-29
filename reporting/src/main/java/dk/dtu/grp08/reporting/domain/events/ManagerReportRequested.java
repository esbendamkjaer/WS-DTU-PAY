package dk.dtu.grp08.reporting.domain.events;


import dk.dtu.grp08.reporting.domain.models.CorrelationId;
import dk.dtu.grp08.reporting.domain.models.user.UserAccountId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author Muhamad Hussein Nadali (s233479)
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerReportRequested {
    CorrelationId correlationId;
}
