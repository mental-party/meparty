package com.teammental.merest.testrestapi;

import com.teammental.merest.autoconfiguration.ApplicationConfiguration;
import com.teammental.merest.EnableRestApi;
import com.teammental.merest.autoconfiguration.StartupApplicationConfiguration;
import com.teammental.merest.autoconfiguration.FilterDtoConverterRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ImportAutoConfiguration({ApplicationConfiguration.class,
    StartupApplicationConfiguration.class,
    FilterDtoConverterRegistrar.class,
})
@EnableRestApi()
public class TestApplicationEnableRestApi {


  public static void main(String[] args) {

    SpringApplication.run(TestApplicationEnableRestApi.class, args);
  }
}
