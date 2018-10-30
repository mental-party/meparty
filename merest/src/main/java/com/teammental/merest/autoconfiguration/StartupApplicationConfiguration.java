package com.teammental.merest.autoconfiguration;

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

  @Autowired(required = false)
  private ApplicationConfiguration applicationConfiguration;

  @Autowired
  private ApplicationExplorer applicationExplorer;

  @Autowired
  private RestApiApplicationRegistry restApiApplicationRegistry;

  @Autowired
  private RestApiApplicationConfigurationProperties restApiApplicationConfigurationProperties;

  private static final Logger LOGGER =
      LoggerFactory.getLogger(StartupApplicationConfiguration.class);

  /**
   * On context refreshed event, merges singleton instance
   * of the applications with custom bean instance of the applications.
   */
  @EventListener(ContextRefreshedEvent.class)
  public void contextRefreshEvent() {

    mergeRestApiApplicationExplorerWithConfigurationProperties();
    
    mergeApplicationExplorerWithConfigurationProperties();
  }

  private void mergeRestApiApplicationExplorerWithConfigurationProperties() {

    if (restApiApplicationConfigurationProperties == null
        || restApiApplicationConfigurationProperties
        .getRestApiApplications() == null) {
      LOGGER.info("Couldn't register rest api applications from resources");
      return;
    }

    restApiApplicationRegistry
        .registerRestApiApplications(restApiApplicationConfigurationProperties
            .getRestApiApplications());

  }

  private void mergeApplicationExplorerWithConfigurationProperties() {
    if (applicationConfiguration == null) {
      LOGGER.info("Couldn't register applications from resources");
    } else {

      for (String key :
          applicationConfiguration.getApplications().keySet()) {
        applicationExplorer.addApplication(key,
            applicationConfiguration.getApplications().get(key));
      }

      if (applicationConfiguration.getUseMockImpl() != null) {
        applicationExplorer
            .setUseMockImpl(applicationConfiguration.getUseMockImpl());
      }
    }

    ApplicationExplorer.setInstance(applicationExplorer);
  }
}
