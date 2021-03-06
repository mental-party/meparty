package com.teammental.merest.testrestapi;

import com.teammental.merest.EnableRestApiProxy;
import com.teammental.merest.autoconfiguration.FilterDtoConverterRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRestApiProxy()
public class TestApplicationEnableRestApi {


  public static void main(String[] args) {

    SpringApplication.run(TestApplicationEnableRestApi.class, args);
  }
}
