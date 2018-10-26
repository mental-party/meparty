package com.teammental.merest.autoconfiguration;

import com.teammental.merest.RestApiProxy;
import com.teammental.merest.RestApiProxyBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestApiProxyConfiguration {

  @Bean
  public RestApiProxy restApiProxy() {
    return new RestApiProxy();
  }

  @Bean(name = "restApiProxyBeanFactory")
  public RestApiProxyBeanFactory restApiProxyBeanFactory() {
    return new RestApiProxyBeanFactory(restApiProxy());
  }
}
