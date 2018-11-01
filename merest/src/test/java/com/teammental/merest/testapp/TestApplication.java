package com.teammental.merest.testapp;

import com.teammental.merest.EnableRestApiServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRestApiServer
public class TestApplication {

  public static void main(String[] args) {

    SpringApplication.run(TestApplication.class, args);
  }
}
