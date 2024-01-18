package dk.dtu.grp08.customer.domain.models.events;


import dk.dtu.grp08.customer.domain.models.CorrelationId;
import dk.dtu.grp08.customer.domain.models.Payment;
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
