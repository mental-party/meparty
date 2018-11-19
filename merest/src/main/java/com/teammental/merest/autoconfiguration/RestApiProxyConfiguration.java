package com.teammental.merest.autoconfiguration;

import com.teammental.merest.RestApiProxyBeanFactory;
import com.teammental.merest.RestApiProxyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestApiProxyConfiguration {

  private static final Logger LOGGER
      = LoggerFactory.getLogger(RestApiProxyConfiguration.class);

  private final RestTemplateBuilder restTemplateBuilder;

  private RestApiApplicationConfigurationProperties restApiApplicationConfigurationProperties;

  /**
   * Constructor.
   *
   * @param restTemplateBuilder                       restTemplateBuilder
   * @param restApiApplicationConfigurationProperties restApiApplicationConfigurationProperties
   */
  @Autowired
  public RestApiProxyConfiguration(RestTemplateBuilder restTemplateBuilder,
                                   RestApiApplicationConfigurationProperties
                                       restApiApplicationConfigurationProperties) {

    this.restTemplateBuilder = restTemplateBuilder;
    this.restApiApplicationConfigurationProperties
        = restApiApplicationConfigurationProperties;
  }

  @Bean
  public ApplicationExplorer applicationExplorer() {

    return new ApplicationExplorer();
  }

  /**
   * RestApiApplicationRegistry bean.
   *
   * @return RestApiApplicationRegistry
   */
  @Bean
  public RestApiApplicationRegistry restApiApplicationRegistry() {

    RestApiApplicationRegistry restApiApplicationRegistry
        = new RestApiApplicationRegistry();

    if (restApiApplicationConfigurationProperties == null
        || restApiApplicationConfigurationProperties
        .getRestApiApplications() == null) {
      LOGGER.info("Couldn't register rest api applications from resources");
    } else {

      restApiApplicationRegistry
          .registerRestApiApplications(restApiApplicationConfigurationProperties
              .getRestApiApplications());
    }
    return restApiApplicationRegistry;
  }

  @Bean
  @ConditionalOnMissingBean(RestTemplate.class)
  public RestTemplate restTemplate() {

    return restTemplateBuilder.build();
  }

  @Bean
  @DependsOn("restApiApplicationRegistry")
  public RestApiProxyHandler restApiProxy() {

    return new RestApiProxyHandler(restTemplate());
  }

  @Bean(name = "restApiProxyBeanFactory")
  @DependsOn("restApiApplicationRegistry")
  public RestApiProxyBeanFactory restApiProxyBeanFactory() {

    return new RestApiProxyBeanFactory(restApiProxy());
  }
}
