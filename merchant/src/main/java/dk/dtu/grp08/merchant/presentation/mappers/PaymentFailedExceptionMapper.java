package dk.dtu.grp08.merchant.presentation.mappers;

import dk.dtu.grp08.merchant.domain.exceptions.PaymentFailedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * @author Muhamad Hussein Nadali (s233479)
 */
@Provider
public class PaymentFailedExceptionMapper implements ExceptionMapper<PaymentFailedException> {
    @Override
    public Response toResponse(PaymentFailedException e) {
        return Response.status(
                Response.Status.BAD_REQUEST
        ).entity(
                e.getMessage()
        ).build();
    }
}
