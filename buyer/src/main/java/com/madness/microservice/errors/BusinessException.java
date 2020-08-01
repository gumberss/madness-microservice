package com.madness.microservice.errors;

public class BusinessException extends Exception {
  public BusinessException(String message){
    super(message);
  }

  public BusinessException(String message, String field){
    super(message);
    this.field = field;
  }

  public String field;
}