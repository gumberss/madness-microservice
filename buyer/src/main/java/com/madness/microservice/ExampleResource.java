package com.madness.microservice;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

@Path("/hello")
public class ExampleResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Timed
    public Response hello() {
        return Response.ok("hello").build();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Counted
    public Response hello2(String message) {
        return Response.ok(message).build();
    }

    
}