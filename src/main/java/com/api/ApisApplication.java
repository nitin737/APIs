package com.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Main application class for the APIs. */
@SpringBootApplication
public class ApisApplication {

  private static final Logger log = LoggerFactory.getLogger(ApisApplication.class);

  /**
   * Main entry point for the Spring Boot application.
   *
   * @param args Command line arguments.
   */
  public static void main(String[] args) {
    log.info("Starting ApisApplication...");
    SpringApplication.run(ApisApplication.class, args);
  }
}
