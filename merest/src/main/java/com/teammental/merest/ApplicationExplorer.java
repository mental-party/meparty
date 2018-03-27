package com.teammental.merest;

import java.util.HashMap;
import java.util.Map;

public class ApplicationExplorer {

  private Map<String, String> applications = new HashMap<>();

  private boolean useMockImpl;

  private static ApplicationExplorer INSTANCE = new ApplicationExplorer();

  private ApplicationExplorer() {
  }

  public String getApplication(String applicationName) {
    return applications.get(applicationName);
  }

  void addApplication(String applicationName, String url) {
    applications.put(applicationName, url);
  }

  void clean() {
    applications = new HashMap<>();
  }

  void removeApplication(String applicationName) {
    applications.remove(applicationName);
  }

  public static ApplicationExplorer getInstance() {
    return INSTANCE;
  }

  void setUseMockImpl(boolean useMockImpl) {
    this.useMockImpl = useMockImpl;
  }

  public boolean getUseMockImpl() {
    return useMockImpl;
  }
}