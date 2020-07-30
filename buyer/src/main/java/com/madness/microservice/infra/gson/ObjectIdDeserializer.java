package com.madness.microservice.infra.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import org.bson.json.JsonParseException;
import org.bson.types.ObjectId;

import java.lang.reflect.Type;

import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.stream.JsonParser;

public class ObjectIdDeserializer implements JsonDeserializer<ObjectId>, JsonbDeserializer<ObjectId> {

  @Override
  public ObjectId deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    return new ObjectId(json.getAsJsonPrimitive().getAsString());
  }

  @Override
  public ObjectId deserialize(JsonParser parser, DeserializationContext ctx, Type rtType) {
     String value = parser.getString();

     return value == null ? null : new ObjectId(value);

  }
}