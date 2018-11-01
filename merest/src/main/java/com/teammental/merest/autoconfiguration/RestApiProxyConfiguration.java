package com.teammental.merest.autoconfiguration;

import com.teammental.merest.RestApiProxyBeanFactory;
import com.teammental.merest.RestApiProxyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestApiProxyConfiguration {

  private final RestTemplateBuilder restTemplateBuilder;

  @Autowired
  public RestApiProxyConfiguration(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplateBuilder = restTemplateBuilder;
  }

  @Bean
  public ApplicationExplorer applicationExplorer() {
    return new ApplicationExplorer();
  }

  @Bean
  public RestApiApplicationRegistry restApiApplicationRegistry() {
    return new RestApiApplicationRegistry();
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
