package com.teammental.merest;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("com.teammental.merest")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationConfiguration {

  private Map<String, String> applications = new HashMap<>();

  private Boolean useMockImpl;

  public Map<String, String> getApplications() {
    return applications;
  }

  public void setUseMockImpl(Boolean useMockImpl) {
    this.useMockImpl = useMockImpl;
  }

  public Boolean getUseMockImpl() {
    return useMockImpl;
  }
}
