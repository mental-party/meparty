package com.teammental.memapper.spring;

import com.teammental.memapper.configuration.MapConfigurationRegistry;
import com.teammental.memapper.core.MapConfigurationRegistrySingleton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupMapConfigurationRegistry {

  @Autowired
  MapConfigurationRegistry mapConfigurationRegistry;

  @EventListener(ContextRefreshedEvent.class)
  public void contextRefreshEvent() {

    MapConfigurationRegistrySingleton.setRegistry(mapConfigurationRegistry);
  }

}
