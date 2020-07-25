package com.madness.microservice.controllers;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.madness.microservice.infra.MongoConnection;
import com.madness.microservice.models.Order;
import com.madness.microservice.models.Product;

import org.bson.types.ObjectId;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

@Path("/buyer/products")
public class ProductController {

    private MongoConnection _conn;

    @Inject

    public ProductController(MongoConnection conn) {
        this._conn = conn;
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Timed
    public Response post() {

        Order p = new Order();
        p.price = 10;
        p.productId = ObjectId.get();
        p.providerId = ObjectId.get();
        p.quantity = 3;

        String mongoConnString = ConfigProvider.getConfig().getValue("mongo.uri", String.class);

        _conn.connect(mongoConnString);
        
        var orders = _conn.db().getCollection("orders", Order.class);
        var a = orders.insertOne(p).getInsertedId();

        return Response.ok(a.toString()).build();
    }
}