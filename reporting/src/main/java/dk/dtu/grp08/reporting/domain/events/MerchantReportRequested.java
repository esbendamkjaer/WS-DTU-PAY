package dk.dtu.grp08.reporting.domain.events;


import dk.dtu.grp08.reporting.domain.models.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantReportRequested {
    UUID id;

}
