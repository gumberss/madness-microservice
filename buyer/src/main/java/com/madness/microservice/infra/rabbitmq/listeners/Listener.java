package com.madness.microservice.infra.rabbitmq.listeners;

import java.io.IOException;

import javax.inject.Inject;

import com.madness.microservice.infra.gson.GsonSerializer;
import com.madness.microservice.infra.rabbitmq.Exchanges;
import com.madness.microservice.infra.rabbitmq.RabbitMqConnection;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public abstract class Listener<T> {

  @Inject
  private RabbitMqConnection _connection;

  @Inject
  GsonSerializer _serializer;

  protected abstract Exchanges exchange();

  protected abstract String queue();

  public void listen(Class<T> clazz) throws IOException {

    _connection.registerQueue(exchange().getValue(), queue());

    var channel = _connection.channel();

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
          throws IOException {
            
        String message = new String(body, "UTF-8");

        System.out.println("message received: " + message);

        var data = _serializer.gson.fromJson(message, clazz);

        long deliveryTag = envelope.getDeliveryTag();

        try {
          consume(data);

          channel.basicAck(deliveryTag, false);
        } catch (Exception ex) {
          System.out.println("An erorr curred consuming data from RabbitMq. Tag:" + consumerTag + "| Message:"
              + message + "| Error: " + ex.getMessage());
          System.out.println(ex);
          channel.basicNack(deliveryTag, false, true);
        }
      }
    };

    _connection.channel().basicConsume(queue(), false, consumer);

  }

  public abstract void consume(T data);
}