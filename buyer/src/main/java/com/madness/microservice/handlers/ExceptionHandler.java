package com.madness.microservice.handlers;

import javax.ws.rs.ext.ExceptionMapper;

import com.madness.microservice.errors.BusinessError;
import com.madness.microservice.errors.BusinessErrorData;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ExceptionHandler implements ExceptionMapper<Exception> {
  @Override
  public Response toResponse(Exception ex) {

    var errorData = new BusinessErrorData("Oh no!! there was a problem :( We'll fix it!!!");
    var error = new BusinessError(errorData);
    return Response.status(Status.INTERNAL_SERVER_ERROR).entity(error).build();
  }
}