package com.madness.microservice.infra.rabbitmq;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import javax.inject.Singleton;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Singleton
public class RabbitMqConnection {

  private ConnectionFactory _connFactory;
  private Connection _connection;
  private Channel _channel;

  public RabbitMqConnection() {
    _connFactory = new ConnectionFactory();
  }

  public RabbitMqConnection withUri(String uri)
      throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException {
    _connFactory.setUri(uri);

    return this;
  }

  public void connect() throws IOException, TimeoutException {
    _connection = _connFactory.newConnection();
    _channel = _connection.createChannel();
  }

  public Connection connection() {
    if (_connection == null) {
      throw new IllegalArgumentException("RabbitMq connection is not set, you must call 'connect' method");
    }

    return _connection;
  }

  public Channel channel() {
    if (_channel == null) {
      throw new IllegalArgumentException("RabbitMq channel is not set, you must call 'connect' method");
    }

    return _channel;
  }

  public void registerQueue(String exchange, String queue) throws IOException {
    _channel.exchangeDeclare(exchange, "fanout" , false);
    _channel.queueDeclare(queue, false, true, false, null);
    _channel.queueBind(queue, exchange, "");
  }
}
