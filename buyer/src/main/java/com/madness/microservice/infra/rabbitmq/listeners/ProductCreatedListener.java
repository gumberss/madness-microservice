package com.madness.microservice.infra.rabbitmq.listeners;

import com.madness.microservice.infra.rabbitmq.Exchanges;
import com.madness.microservice.infra.rabbitmq.events.ProductCreatedEvent;

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