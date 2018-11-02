package com.teammental.merest.testapp;

import com.teammental.merest.EnableRestApiServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRestApiServer
@EnableAutoConfiguration(exclude = {
    org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class
})
public class TestApplication {

  public static void main(String[] args) {

    SpringApplication.run(TestApplication.class, args);
  }
}
