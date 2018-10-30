package com.teammental.merest.autoconfiguration;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "com.teammental.merest")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestApiApplicationConfigurationProperties {

  private List<RestApiApplication> restApiApplications;

  public List<RestApiApplication> getRestApiApplications() {
    return restApiApplications;
  }

  public void setRestApiApplications(List<RestApiApplication> restApiApplications) {
    this.restApiApplications = restApiApplications;
  }

}
