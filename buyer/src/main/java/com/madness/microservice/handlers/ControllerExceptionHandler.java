package com.madness.microservice.handlers;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Provider
public class ControllerExceptionHandler implements ExceptionMapper<ConstraintViolationException> {
  @Override
  public Response toResponse(ConstraintViolationException ex) {
    var constrainErrors = ex.getConstraintViolations();

    var returnErrors = constrainErrors.stream()
    .map(x -> new BusinessError(x.getMessage(), x.getPropertyPath().toString()))
    .collect(Collectors.toList());

    return Response.status(Status.BAD_REQUEST).entity(new BusinessException(returnErrors)).build();
  }

  public class BusinessError {
    BusinessError(String message, String field){
      this.message = message;
      this.field = field;
    }
    public String message;
    public String field;
  }
  public class BusinessException {
    BusinessException(List<BusinessError> errors){
      this.errors = errors;
    }
    public List<BusinessError> errors;
  }
}