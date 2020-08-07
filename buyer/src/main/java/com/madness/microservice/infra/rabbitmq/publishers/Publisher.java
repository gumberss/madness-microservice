package com.madness.microservice.infra.rabbitmq.publishers;

import java.io.IOException;

import com.google.gson.Gson;
import com.madness.microservice.infra.gson.GsonSerializer;
import com.madness.microservice.infra.rabbitmq.Exchanges;
import com.madness.microservice.infra.rabbitmq.RabbitMqConnection;

public abstract class Publisher<T> {

  private RabbitMqConnection _rabbitMq;
  private Gson _gson;

  protected abstract Exchanges exchange();

  protected Publisher() {
  }

  public Publisher(RabbitMqConnection rabbitMq, GsonSerializer serializer) {
    _rabbitMq = rabbitMq;
    _gson = serializer.gson;
  }

  public void publish(T data) throws IOException {

    var bytes = _gson.toJson(data).getBytes();

    var channel = _rabbitMq.connection().createChannel();

    channel.exchangeDeclare(exchange().getValue(), "fanout", false);
    
    channel.basicPublish(exchange().getValue(), "", null, bytes);
  }
}