package com.teammental.merest.testapp;

import com.teammental.merest.ApplicationConfiguration;
import com.teammental.merest.StartupApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ImportAutoConfiguration({ApplicationConfiguration.class,
    StartupApplicationConfiguration.class})
public class TestApplication {

  public static void main(String[] args) {
    SpringApplication.run(TestApplication.class, args);
  }
}
