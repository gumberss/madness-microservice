package com.madness.microservice.errors;

import java.util.Arrays;
import java.util.List;

public class BusinessError {

  public BusinessError(BusinessErrorData error){
    this.errors = Arrays.asList(error);
  }

  public BusinessError(List<BusinessErrorData> errors){
    this.errors = errors;
  }

  public List<BusinessErrorData> errors;
}