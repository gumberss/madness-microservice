package com.madness.microservice.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

@Path("/buyer/products")
public class ProductController {

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Timed
    public Response post() {

        return Response.ok("Product received").build();
    }
}