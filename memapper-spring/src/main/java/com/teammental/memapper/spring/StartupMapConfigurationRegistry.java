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

  /**
   * On context refreshed event, merges singleton instance
   * of the registry with custom bean instance of the registry.
   */
  @EventListener(ContextRefreshedEvent.class)
  public void contextRefreshEvent() {

    MapConfigurationRegistrySingleton.getSingleton()
        .merge(mapConfigurationRegistry);
  }

}
