package com.madness.microservice.handlers;

import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.madness.microservice.errors.BusinessError;
import com.madness.microservice.errors.BusinessErrorData;
import com.madness.microservice.errors.BusinessException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Provider
public class BusinessExceptionHandler  implements ExceptionMapper<BusinessException> {
  @Override
  public Response toResponse(BusinessException ex) {
    var returnErrors =new BusinessErrorData(ex.getMessage(), ex.field);

    return Response.status(Status.BAD_REQUEST).entity(new BusinessError(returnErrors)).build();
  }

}