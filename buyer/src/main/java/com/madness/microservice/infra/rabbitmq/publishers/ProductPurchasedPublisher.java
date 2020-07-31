package com.madness.microservice.infra.rabbitmq.publishers;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.madness.microservice.infra.gson.GsonSerializer;
import com.madness.microservice.infra.rabbitmq.Exchanges;
import com.madness.microservice.infra.rabbitmq.RabbitMqConnection;
import com.madness.microservice.infra.rabbitmq.events.ProductPurchasedEvent;

@ApplicationScoped
public class ProductPurchasedPublisher extends Publisher<ProductPurchasedEvent> {

  protected ProductPurchasedPublisher() {
    super();
  }

  @Inject
  public ProductPurchasedPublisher(RabbitMqConnection rabbitMq, GsonSerializer serializer) {
    super(rabbitMq, serializer);
  }

  @Override
  protected Exchanges exchange() {
    return Exchanges.ProductPurchased;
  }

}