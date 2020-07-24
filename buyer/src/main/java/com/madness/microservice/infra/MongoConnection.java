package com.madness.microservice.infra;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.MongoClientSettings;

public class MongoConnection {
  public void connect() {

    ConnectionString connectionString = new ConnectionString("mongodb://buyer-mongo-srv:27017");
    MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString)
        .retryWrites(true).build();

    MongoClient mongoClient = MongoClients.create(settings);

    var db = mongoClient.getDatabase("buyer");

    // import com.mongodb.client.MongoCollection;
    // MongoCollection<Product> products = db.getCollection("products",
    // Product.class);
  }


}