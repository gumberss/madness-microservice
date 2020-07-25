package com.madness.microservice.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.madness.microservice.models.Order;
import com.mongodb.client.model.Filters;

import org.bson.types.ObjectId;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.metrics.annotation.Timed;

import com.google.gson.Gson;
import com.madness.microservice.infra.gson.GsonSerializer;
import com.madness.microservice.infra.mongo.MongoConnection;

@Path("/buyer/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderController {

    private MongoConnection _conn;
    private Gson gson;

    @Inject
    public OrderController(MongoConnection conn, GsonSerializer serializer) {
        this._conn = conn;
        this.gson = serializer.gson;
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Timed
    public Response post(Order order) {

        String mongoConnString = ConfigProvider.getConfig().getValue("mongo.uri", String.class);

        var orders = _conn.collection("order", Order.class);
        
        var a = orders.insertOne(order).getInsertedId();
        
        return Response.ok(a.toString()).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Timed
    public Response get() {

        String mongoConnString = ConfigProvider.getConfig().getValue("mongo.uri", String.class);

        _conn.connect(mongoConnString);
        
        var orders = _conn.collection("order", Order.class);
        
        var a = orders.find().cursor();


        List<Order> b = new ArrayList<Order>();

        
        while(a.hasNext()){
            var or = a.next();
            b.add(or);
        }
        
        return Response.ok(gson.toJson(b)).build();
    }
}