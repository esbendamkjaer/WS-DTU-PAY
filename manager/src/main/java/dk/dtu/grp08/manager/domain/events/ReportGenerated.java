package dk.dtu.grp08.manager.domain.events;


import dk.dtu.grp08.manager.domain.models.CorrelationId;
import dk.dtu.grp08.manager.domain.models.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportGenerated {
    List<Payment> report;

    CorrelationId correlationId;

}
