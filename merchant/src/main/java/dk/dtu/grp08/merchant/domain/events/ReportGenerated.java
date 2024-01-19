package dk.dtu.grp08.merchant.domain.events;



import dk.dtu.grp08.merchant.domain.models.CorrelationId;
import dk.dtu.grp08.merchant.domain.models.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * @author Clair Norah Mutebi (s184187)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportGenerated {
    List<Payment> report;

    CorrelationId correlationId;

}
