package dk.dtu.grp08.customer.domain.events;


import dk.dtu.grp08.customer.domain.models.CorrelationId;
import dk.dtu.grp08.customer.domain.models.UserAccountId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerReportRequested {

    /**
     *
     * @author Fuad
     */
    UserAccountId id;
    CorrelationId correlationId;

}
