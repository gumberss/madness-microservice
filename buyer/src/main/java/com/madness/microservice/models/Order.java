package com.madness.microservice.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;

public class Order {

  public ObjectId id;

  @NotNull(message = "Provider must be valid")
  public Provider provider;

  @NotNull(message = "Product must be valid")
  public Product product;

  @Min(value = 1, message = "Minimun quantity is 1")
  @Max(value = 1_000_000, message = "This is more than we can handle yet")
  public int quantity;

  @Min(value = 1, message = "Minimun price is 1")
  @Max(value = 1_000_000_000, message = "We can't pay this price yet")
  public double price;

  @Min(value = 0, message = "Type must be valid")
  @Max(value = 2, message = "Type must be valid")
  public int type;
}