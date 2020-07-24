package com.madness.microservice.controllers;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Readiness
public class ReadinessCheck implements HealthCheck {
  @Override
  public HealthCheckResponse call(){
    return HealthCheckResponse.up("Buyer microservice is ready!");
  }
}