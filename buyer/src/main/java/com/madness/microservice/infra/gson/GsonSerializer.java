package com.madness.microservice.infra.gson;

import javax.inject.Singleton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.bson.types.ObjectId;

@Singleton
public class GsonSerializer {

  public Gson gson;

  public GsonSerializer() {
    var builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdSerializer());
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdDeserializer());

    gson = builder.create();
  }

}
