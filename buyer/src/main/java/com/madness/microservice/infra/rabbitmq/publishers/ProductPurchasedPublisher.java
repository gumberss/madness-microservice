package com.madness.microservice.infra.rabbitmq.publishers;

import javax.inject.Inject;

import com.madness.microservice.infra.gson.GsonSerializer;
import com.madness.microservice.infra.rabbitmq.Exchanges;
import com.madness.microservice.infra.rabbitmq.RabbitMqConnection;
import com.madness.microservice.infra.rabbitmq.events.ProductPurchasedEvent;

public class ProductPurchasedPublisher extends Publisher<ProductPurchasedEvent> {

  @Inject
  public ProductPurchasedPublisher(RabbitMqConnection rabbitMq, GsonSerializer serializer) {
    super(rabbitMq, serializer);
  }

  @Override
  protected Exchanges exchange() {
    return Exchanges.ProductPurchased;
  }
  
}