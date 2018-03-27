package com.teammental.merest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class StartupApplicationConfiguration {

  @Autowired
  private ApplicationConfiguration applicationConfiguration;

  private static final Logger LOGGER =
      LoggerFactory.getLogger(StartupApplicationConfiguration.class);

  /**
   * On context refreshed event, merges singleton instance
   * of the applications with custom bean instance of the applications.
   */
  @EventListener(ContextRefreshedEvent.class)
  public void contextRefreshEvent() {

    if (applicationConfiguration == null) {
      LOGGER.info("Couldn't register applications from resources");
      return;
    }

    for (String key :
        applicationConfiguration.getApplications().keySet()) {
      ApplicationExplorer.getInstance().addApplication(key,
          applicationConfiguration.getApplications().get(key));
    }

    if (applicationConfiguration.getUseMockImpl() != null) {
      ApplicationExplorer.getInstance()
          .setUseMockImpl(applicationConfiguration.getUseMockImpl());
    }
  }
}
