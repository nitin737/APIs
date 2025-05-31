package com.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Main application class for the APIs. */
@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(title = "Task API", version = "1.0", description = "API for managing tasks"))
public class ApisApplication {

  /**
   * Main entry point for the Spring Boot application.
   *
   * @param args Command line arguments.
   */
  public static void main(String[] args) {
    SpringApplication.run(ApisApplication.class, args);
  }
}
