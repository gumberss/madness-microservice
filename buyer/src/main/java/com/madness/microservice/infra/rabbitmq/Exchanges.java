package com.madness.microservice.infra.rabbitmq;

public enum Exchanges {
  ProductCreated("product:created");

  private String _exchange;

  Exchanges(String exchange){
    _exchange = exchange;
  }

  public String getValue(){
    return _exchange;
  }
}