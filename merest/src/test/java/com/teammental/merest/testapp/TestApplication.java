package com.teammental.merest.testapp;

import com.teammental.merest.autoconfiguration.ApplicationConfiguration;
import com.teammental.merest.autoconfiguration.FilterDtoConverterRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ImportAutoConfiguration({ApplicationConfiguration.class,
    FilterDtoConverterRegistrar.class,
})
public class TestApplication {

  public static void main(String[] args) {

    SpringApplication.run(TestApplication.class, args);
  }
}
