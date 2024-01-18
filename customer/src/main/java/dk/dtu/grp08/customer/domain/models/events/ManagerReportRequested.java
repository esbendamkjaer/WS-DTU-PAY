package dk.dtu.grp08.customer.domain.models.events;


import dk.dtu.grp08.customer.domain.models.CorrelationId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerReportRequested {
    CorrelationId correlationId;


}
