package com.madness.microservice.infra.rabbitmq.listeners;

import javax.inject.Singleton;

import com.madness.microservice.infra.rabbitmq.Exchanges;
import com.madness.microservice.infra.rabbitmq.events.ProductCreatedEvent;

@Singleton
public class ProductCreatedListener extends Listener<ProductCreatedEvent> {

  @Override
  protected Exchanges exchange() {
   return Exchanges.ProductCreated; 
  }

  @Override
  protected String queue() {
    return "buyer:product:created";
  }

  @Override
  public void consume(ProductCreatedEvent data) {
    
    

  }
  
}