package com.teammental.merest.autoconfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(RestApiApplicationRegistry.class)
public class StartupApplicationConfiguration {


  private final ApplicationConfiguration applicationConfiguration;

  private final ApplicationExplorer applicationExplorer;

  private final RestApiApplicationRegistry restApiApplicationRegistry;

  private RestApiApplicationConfigurationProperties restApiApplicationConfigurationProperties;

  /**
   * Constructor.
   * @param restApiApplicationConfigurationProperties restApiApplicationConfigurationProperties
   * @param applicationConfiguration applicationConfiguration
   * @param applicationExplorer applicationExplorer
   * @param restApiApplicationRegistry restApiApplicationRegistry
   */
  @Autowired
  public StartupApplicationConfiguration(RestApiApplicationConfigurationProperties
                                             restApiApplicationConfigurationProperties,
                                         ApplicationConfiguration applicationConfiguration,
                                         ApplicationExplorer applicationExplorer,
                                         RestApiApplicationRegistry restApiApplicationRegistry) {

    this.restApiApplicationConfigurationProperties
        = restApiApplicationConfigurationProperties;

    this.applicationConfiguration = applicationConfiguration;
    this.applicationExplorer = applicationExplorer;
    this.restApiApplicationRegistry = restApiApplicationRegistry;

    contextRefreshEvent();
  }

  private static final Logger LOGGER =
      LoggerFactory.getLogger(StartupApplicationConfiguration.class);

  /**
   * Merges singleton instance
   * of the applications with custom bean instance of the applications.
   */
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
