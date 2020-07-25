package com.madness.microservice.models;

import org.bson.types.ObjectId;

public class Order {
  public ObjectId id; 
  public ObjectId providerId;
  public ObjectId productId;
  public int quantity;
  public double price;
}