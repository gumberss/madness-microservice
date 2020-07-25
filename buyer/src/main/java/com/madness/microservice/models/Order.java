package com.madness.microservice.models;

import org.bson.types.ObjectId;

public class Order{
  public ObjectId id; 
  public Provider provider;
  public Product product;
  public int quantity;
  public double price;
  public int type;
}