package com.madness.microservice.infra.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.client.MongoClient;
import com.mongodb.MongoClientSettings;
import javax.inject.Singleton;

@Singleton
public class MongoConnection {

  private MongoDatabase _db;

  private boolean _connected = false;

  public void connect(String connString) {

    if(_connected) return;

    ConnectionString connectionString = new ConnectionString(connString);
    MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString)
        .retryWrites(true).build();

    MongoClient mongoClient = MongoClients.create(settings);

    CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
        CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

    _db = mongoClient.getDatabase("buyer").withCodecRegistry(pojoCodecRegistry);

    // import com.mongodb.client.MongoCollection;
    // MongoCollection<Product> products = db.getCollection("products",
    // Product.class);

    _connected = true;
  }

  public MongoDatabase db() {
    if (_db == null) {
      throw new IllegalArgumentException("The database wasn't initiated, the connect method should be called first");
    }

    return this._db;
  }

  public <T> MongoCollection<T> collection(String collectionName, Class<T> collectionType){
      return _db.getCollection(collectionName, collectionType);
  }
}