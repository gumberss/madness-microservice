package com.madness.microservice.handlers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.madness.microservice.errors.BusinessError;
import com.madness.microservice.errors.BusinessErrorData;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Provider
public class ConstraintViolationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {
  @Override
  public Response toResponse(ConstraintViolationException ex) {
    var constrainErrors = ex.getConstraintViolations();

    var returnErrors = constrainErrors.stream()
    .map(x -> new BusinessErrorData(x.getMessage(), x.getPropertyPath().toString()))
    .collect(Collectors.toList());

    return Response.status(Status.BAD_REQUEST).entity(new BusinessError(returnErrors)).build();
  }

}