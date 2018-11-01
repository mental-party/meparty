package com.teammental.merest.testappbasicauth;

import com.teammental.merest.autoconfiguration.FilterDtoConverterRegistrar;
import com.teammental.merest.autoconfiguration.RestApiApplicationConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ImportAutoConfiguration({
    FilterDtoConverterRegistrar.class
})
public class TestApplicationBasicAuth {

  public static void main(String[] args) {

    SpringApplication.run(TestApplicationBasicAuth.class, args);
  }
}
