package com.teammental.merest;

import java.util.HashMap;
import java.util.Map;

public class ApplicationExplorer {
  private static Map<String, String> applicationUrlMap = new HashMap<>();

  public static String getApplicationUrl(String applicationName) {
    return applicationUrlMap.get(applicationName);
  }

  static void addApplicationUrl(String applicationName, String url) {
    applicationUrlMap.put(applicationName, url);
  }
}
