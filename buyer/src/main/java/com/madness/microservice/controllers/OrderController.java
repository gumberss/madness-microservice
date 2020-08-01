package com.madness.microservice.controllers;

import java.util.ArrayList;
import java.util.List;

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
import com.madness.microservice.models.Product;
import com.madness.microservice.models.Provider;

import org.eclipse.microprofile.metrics.annotation.Timed;
import org.springframework.web.bind.annotation.RequestBody;

import com.google.gson.Gson;
import com.madness.microservice.errors.BusinessException;
import com.madness.microservice.infra.gson.GsonSerializer;
import com.madness.microservice.infra.mongo.MongoDbConnection;
import com.madness.microservice.infra.rabbitmq.events.ProductPurchasedEvent;
import com.madness.microservice.infra.rabbitmq.publishers.ProductPurchasedPublisher;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

@Path("/buyer/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderController {

    private MongoDbConnection _conn;
    private Gson _gson;
    private ProductPurchasedPublisher _productPurchasedPublisher;
    private MongoCollection<Order> _orderCollection;

    @Inject
    public OrderController(MongoDbConnection conn, GsonSerializer serializer,
            ProductPurchasedPublisher productPurchasedPublisher) {
        this._conn = conn;
        this._gson = serializer.gson;
        this._productPurchasedPublisher = productPurchasedPublisher;
        _orderCollection = _conn.collection("orders", Order.class);
    }

    @POST
    public Response post(@Valid @RequestBody Order order) throws Exception {
        _orderCollection.insertOne(order);

        var productCollection = _conn.collection("products", Product.class);
        var byProductId = Filters.eq("_id", order.product.id);
        var product = productCollection.find(byProductId).first();
        if (product == null) {
            throw new BusinessException("It is possible to buy only registered products", "product");
        }

        var providerCollection = _conn.collection("providers", Provider.class);
        var byProviderId = Filters.eq("_id", order.provider.id);
        var provider = providerCollection.find(byProviderId).first();
        if (provider == null) {
            throw new BusinessException("It is possible to buy only from registered providers", "provider");
        }

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
    @Timed
    public Response get() {
        var ordersCursor = _orderCollection.find().cursor();

        List<Order> orders = new ArrayList<Order>();

        while (ordersCursor.hasNext()) {
            var order = ordersCursor.next();
            orders.add(order);
        }

        return Response.ok(_gson.toJson(orders)).build();
    }
}