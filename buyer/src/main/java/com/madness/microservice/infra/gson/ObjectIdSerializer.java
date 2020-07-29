package com.madness.microservice.infra.gson;

import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.bson.types.ObjectId;

import java.lang.reflect.Type;

import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;

import com.google.gson.JsonElement;

public class ObjectIdSerializer implements JsonSerializer<ObjectId>, JsonbSerializer<ObjectId> {
  public JsonElement serialize(ObjectId src, Type typeOfSrc, JsonSerializationContext context) {
    return new JsonPrimitive(src.toString());
  }

  @Override
  public void serialize(ObjectId obj, JsonGenerator generator, SerializationContext ctx) {
    ctx.serialize(obj, generator.write(obj.toString()));
  }
}