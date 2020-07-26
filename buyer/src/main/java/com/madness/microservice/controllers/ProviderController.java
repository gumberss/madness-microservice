package com.madness.microservice.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.madness.microservice.infra.gson.GsonSerializer;
import com.madness.microservice.infra.mongo.MongoDbConnection;
import com.madness.microservice.models.Provider;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;

import org.bson.types.ObjectId;
import org.eclipse.microprofile.config.ConfigProvider;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/buyer/providers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProviderController {

    private MongoCollection<Provider> _collection;
    private Gson gson;

    @Inject
    public ProviderController(MongoDbConnection conn, GsonSerializer serializer) {
        String mongoConnString = ConfigProvider.getConfig().getValue("mongo.uri", String.class);

        // conn.connect(mongoConnString);
        this._collection = conn.collection("providers", Provider.class);
        this.gson = serializer.gson;
    }

    @GET
    public Response get() {

        List<Provider> providers = new ArrayList<Provider>();

        this._collection.find().into(providers);

        return Response.ok(gson.toJson(providers)).build();
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam String id) {

        var provider = this._collection.find(Filters.eq("_id", new ObjectId(id))).first();

        return Response.ok(gson.toJson(provider)).build();
    }

    @POST
    public Response post(Provider provider) {

        this._collection.insertOne(provider).getInsertedId();

        return Response.ok(gson.toJson(provider)).build();
    }

    @PUT
    @Path("{id}")
    public Response put(@PathParam String id, Provider provider) {

        var updatedProvider = this._collection.findOneAndReplace(Filters.eq("_id", new ObjectId(id)), provider,
                new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER));

        return Response.ok(gson.toJson(updatedProvider)).build();
    }
}