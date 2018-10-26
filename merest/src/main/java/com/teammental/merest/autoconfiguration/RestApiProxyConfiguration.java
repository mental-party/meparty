package com.teammental.merest.autoconfiguration;

import com.teammental.merest.RestApiProxy;
import com.teammental.merest.RestApiProxyBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
  @ConditionalOnMissingBean(RestTemplate.class)
  public RestTemplate restTemplate() {
    return restTemplateBuilder.build();
  }

  @Bean
  public RestApiProxy restApiProxy() {
    return new RestApiProxy(restTemplate());
  }

  @Bean(name = "restApiProxyBeanFactory")
  public RestApiProxyBeanFactory restApiProxyBeanFactory() {
    return new RestApiProxyBeanFactory(restApiProxy());
  }
}
