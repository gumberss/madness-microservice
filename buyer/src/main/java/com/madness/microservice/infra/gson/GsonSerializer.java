package com.madness.microservice.infra.gson;

import java.lang.reflect.Type;

import javax.inject.Singleton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

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

  public class ObjectIdSerializer implements JsonSerializer<ObjectId> {
    public JsonElement serialize(ObjectId src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(src.toString());
    }
  }

  public class ObjectIdDeserializer implements JsonDeserializer<ObjectId> {

	@Override
	public ObjectId deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
        return new ObjectId(json.getAsJsonPrimitive().getAsString());
      }
	}   
}

