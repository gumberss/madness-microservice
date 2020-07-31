package com.madness.microservice.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.madness.microservice.models.Order;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.madness.microservice.infra.gson.GsonSerializer;
import com.madness.microservice.infra.mongo.MongoDbConnection;
import com.madness.microservice.infra.rabbitmq.RabbitMqConnection;
import com.madness.microservice.infra.rabbitmq.events.ProductPurchasedEvent;
import com.madness.microservice.infra.rabbitmq.publishers.ProductPurchasedPublisher;
import javax.ws.rs.core.Response.Status;

@Path("/buyer/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderController {

    private MongoDbConnection _conn;
    private Gson _gson;
    private RabbitMqConnection _rabbitMq;
    private ProductPurchasedPublisher _productPurchasedPublisher;

    @Inject
    public OrderController(MongoDbConnection conn, GsonSerializer serializer, RabbitMqConnection rabbitMq,
            ProductPurchasedPublisher productPurchasedPublisher) {
        this._conn = conn;
        this._gson = serializer.gson;
        this._rabbitMq = rabbitMq;
        this._productPurchasedPublisher = productPurchasedPublisher;
    }

    @POST
    public Response post(@Valid @RequestBody Order order) throws Exception {
        var orders = _conn.collection("orders", Order.class);

        orders.insertOne(order);

        var productPurchasedEvent = new ProductPurchasedEvent();
        productPurchasedEvent.id = order.id.toString();
        productPurchasedEvent.price = order.price;
        productPurchasedEvent.productId = order.product.id.toString();
        productPurchasedEvent.providerId = order.provider.id.toString();
        productPurchasedEvent.quantity = order.quantity;
        productPurchasedEvent.type = order.type;
        try {
            _productPurchasedPublisher.publish(productPurchasedEvent);
        } catch (Exception ex) {
            System.out.println("There was a problem sending product purchased event: " + ex.getMessage() + "| Ex: "
                    + ex.toString());
                throw ex;
        }

        return Response.ok(_gson.toJson(order)).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Timed
    public Response get() {
        var orders = _conn.collection("order", Order.class);

        var a = orders.find().cursor();

        List<Order> b = new ArrayList<Order>();

        while (a.hasNext()) {
            var or = a.next();
            b.add(or);
        }

        return Response.ok(_gson.toJson(b)).build();
    }
}