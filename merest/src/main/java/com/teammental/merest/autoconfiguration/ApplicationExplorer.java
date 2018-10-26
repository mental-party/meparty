package com.teammental.merest.autoconfiguration;

import java.util.HashMap;
import java.util.Map;

public class ApplicationExplorer {

  public ApplicationExplorer() {
  }

  private Map<String, String> applications = new HashMap<>();

  private boolean useMockImpl;


  public String getApplication(String applicationName) {
    return applications.get(applicationName);
  }

  public void addApplication(String applicationName, String url) {
    applications.put(applicationName, url);
  }

  public void clean() {
    applications = new HashMap<>();
  }

  public void removeApplication(String applicationName) {
    applications.remove(applicationName);
  }

  public void setUseMockImpl(boolean useMockImpl) {
    this.useMockImpl = useMockImpl;
  }

  public boolean getUseMockImpl() {
    return useMockImpl;
  }


  // will be removed
  // used only for backward compatibilty
  private static ApplicationExplorer INSTANCE = new ApplicationExplorer();

  // will be removed
  // used only for backward compatibilty
  @Deprecated
  public static ApplicationExplorer getInstance() {
    return INSTANCE;
  }

  // will be removed
  // used only for backward compatibilty
  static void setInstance(ApplicationExplorer applicationExplorer) {
    INSTANCE = applicationExplorer;
  }
}