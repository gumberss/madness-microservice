package com.madness.microservice.models;

import org.bson.types.ObjectId;

public class Product {

  public Product(ObjectId id) {
    this.id = id;
  }

  public Product(String id) {
    this.id = new ObjectId(id);
  }

  public Product() {
  }

  public ObjectId id;
}