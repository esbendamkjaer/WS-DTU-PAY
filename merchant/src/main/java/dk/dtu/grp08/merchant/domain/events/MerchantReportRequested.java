package dk.dtu.grp08.merchant.domain.events;


import dk.dtu.grp08.merchant.domain.models.CorrelationId;
import dk.dtu.grp08.merchant.domain.models.UserId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Fuad Hassan Jama (s233468)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantReportRequested {
    UserId id;
    CorrelationId correlationId;


}
