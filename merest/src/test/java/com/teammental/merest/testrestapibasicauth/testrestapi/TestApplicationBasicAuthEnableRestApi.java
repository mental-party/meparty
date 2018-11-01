package com.teammental.merest.testrestapibasicauth.testrestapi;

import com.teammental.merest.EnableRestApiProxy;
import com.teammental.merest.autoconfiguration.FilterDtoConverterRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRestApiProxy()
public class TestApplicationBasicAuthEnableRestApi {


  public static void main(String[] args) {

    SpringApplication.run(TestApplicationBasicAuthEnableRestApi.class, args);
  }
}
