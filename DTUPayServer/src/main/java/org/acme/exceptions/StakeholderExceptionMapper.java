package org.acme.exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class StakeholderExceptionMapper implements ExceptionMapper<StakeholderException> {

@Override
public Response toResponse(StakeholderException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
        .entity(exception.getMessage())
        .type(MediaType.TEXT_PLAIN)
        .build();
        }
        }
