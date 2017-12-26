package com.teammental.memapper.spring;

import com.teammental.memapper.configuration.MapConfiguration;
import com.teammental.memapper.configuration.MapConfigurationBuilder;
import com.teammental.memapper.configuration.MapConfigurationRegistry;
import com.teammental.memapper.spring.to.TargetTo;
import com.teammental.memapper.spring.to.SourceTo;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

  @Bean
  public MapConfigurationRegistry mapConfigurationRegistry() {
    MapConfigurationRegistry registry = new MapConfigurationRegistry();

    MapConfiguration configuration = MapConfigurationBuilder
        .twoWayMapping()
        .between(SourceTo.class)
        .and(TargetTo.class)
        .mapField("id").with("no")
        .mapField("name").with("title")
        .build();

    registry.register(configuration);

    return registry;
  }
}
