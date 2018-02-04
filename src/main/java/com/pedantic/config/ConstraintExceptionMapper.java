package com.pedantic.config;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Provider
public class ConstraintExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Inject
    Logger logger;


    @Override
    public Response toResponse(ConstraintViolationException e) {
        final Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation vi : e.getConstraintViolations()) {
            String message = vi.getMessage();
            String propertyName = vi.getPropertyPath().toString();

            errors.put(propertyName, message);
        }

        return Response.status(Response.Status.PRECONDITION_FAILED)
                .entity(errors).build();

    }
}
