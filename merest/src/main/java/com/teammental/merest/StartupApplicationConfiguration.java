package com.teammental.merest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupApplicationConfiguration {

  @Autowired
  private ApplicationConfiguration applicationConfiguration;

  /**
   * On context refreshed event, merges singleton instance
   * of the applications with custom bean instance of the applications.
   */
  @EventListener(ContextRefreshedEvent.class)
  public void contextRefreshEvent() {

    if (applicationConfiguration == null) {
      return;
    }

    for (String key:
        applicationConfiguration.getApplications().keySet()) {
      ApplicationExplorer.getInstance().addApplication(key,
          applicationConfiguration.getApplications().get(key));
    }
  }
}
