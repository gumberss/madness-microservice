package com.madness.microservice.infra.gson;

import io.quarkus.jsonb.JsonbConfigCustomizer;
import javax.inject.Singleton;
import javax.json.bind.JsonbConfig;

@Singleton
public class OrderSerializerRegistrationCustomizer implements JsonbConfigCustomizer {
  public void customize(JsonbConfig config) {
    config.withSerializers(new ObjectIdSerializer());
    config.withDeserializers(new ObjectIdDeserializer());
  }
}