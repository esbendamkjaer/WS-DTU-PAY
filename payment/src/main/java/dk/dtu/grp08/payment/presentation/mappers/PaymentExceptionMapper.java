package dk.dtu.grp08.payment.presentation.mappers;

import dk.dtu.grp08.payment.domain.exceptions.PaymentException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class PaymentExceptionMapper implements ExceptionMapper<PaymentException> {
    @Override
    public Response toResponse(PaymentException e) {
        return Response.status(
                    Response.Status.BAD_REQUEST
                )
                .entity(
                    e.getMessage()
                ).build();
    }
}
