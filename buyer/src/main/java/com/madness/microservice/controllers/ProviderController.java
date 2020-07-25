package com.madness.microservice.controllers;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.madness.microservice.infra.mongo.MongoConnection;
import com.madness.microservice.models.Provider;
import com.mongodb.client.MongoCollection;

import org.bson.types.ObjectId;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/buyer/providers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProviderController {

    private MongoConnection _conn;
    private MongoCollection<Provider> _collection;

    @Inject
    public ProviderController(MongoConnection conn) {
        this._conn = conn;
//        this._collection = conn.collection("provider", Provider.class);
    }

    @GET
    public Response get() {
        return Response.ok(this._collection.find()).build();
    }

    @POST
    public Response post(Provider provider) {

        var id = this._collection.insertOne(provider).getInsertedId();

        return Response.ok(provider).build();
    }

    @PUT
    @Path("{id}")
    public Response put(@PathParam ObjectId id, Provider provider) {

        return Response.ok("Provider updated").build();
    }
}