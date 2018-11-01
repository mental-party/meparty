package com.teammental.merest.testappbasicauth;

import com.teammental.merest.EnableRestApiServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableRestApiServer
@Import(TestApplicaationBasicAuthConfiguration
    .class)
public class TestApplicationBasicAuth {

  public static void main(String[] args) {

    SpringApplication.run(TestApplicationBasicAuth.class, args);
  }
}
