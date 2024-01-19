package dk.dtu.grp08.manager.domain.events;


import dk.dtu.grp08.manager.domain.models.CorrelationId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Dilara Eda Celepli (s184262)
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerReportRequested {
    CorrelationId correlationId;
}
