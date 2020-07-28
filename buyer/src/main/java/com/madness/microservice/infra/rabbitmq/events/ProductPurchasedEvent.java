package com.madness.microservice.infra.rabbitmq.events;

public class ProductPurchasedEvent {
  public String id;
  public String providerId;
  public String productId;
  public int quantity;
  public double price;
  public int type;
}