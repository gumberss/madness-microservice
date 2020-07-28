package com.madness.microservice.infra.rabbitmq.listeners;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.madness.microservice.infra.mongo.MongoDbConnection;
import com.madness.microservice.infra.rabbitmq.Exchanges;
import com.madness.microservice.infra.rabbitmq.events.ProductCreatedEvent;
import com.madness.microservice.models.Product;
import com.mongodb.client.MongoCollection;

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

  @Inject
  public MongoDbConnection conn;

  @Override
  public void consume(final ProductCreatedEvent data) {

    System.out.println(data.id);

    var collection = conn.collection("products", Product.class);

    var product = new Product(data.id);

    System.out.println("Inserting new product");

    collection.insertOne(product);

    System.out.println("Product inserted.");
  }

}