package com.teammental.memapper.spring;

import com.teammental.memapper.configuration.MapConfigurationRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupMapConfigurationRegistryBeanLoader {


  /**
   * Default Bean definition for MapConfigurationRegistry.
   * @return MapConfigurationRegistry.
   */
  @Bean
  @ConditionalOnMissingBean(MapConfigurationRegistry.class)
  public MapConfigurationRegistry mapConfigurationRegistry() {

    MapConfigurationRegistry registry = new MapConfigurationRegistry();
    return registry;
  }
}
