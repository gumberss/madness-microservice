package com.madness.microservice.errors;

public class BusinessErrorData {
  public BusinessErrorData(String message){
    this.message = message;
  }

  public BusinessErrorData(String message, String field){
    this.message = message;
    this.field = field;
  }
  public String message;
  public String field;
}