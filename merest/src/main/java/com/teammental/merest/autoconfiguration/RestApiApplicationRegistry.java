package com.teammental.merest.autoconfiguration;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class RestApiApplicationRegistry {

  private Set<RestApiApplication> restApiApplications;

  public RestApiApplicationRegistry() {
    this.restApiApplications = new HashSet<>();
  }

  public Set<RestApiApplication> getRestApiApplications() {
    return restApiApplications;
  }

  /**
   * Gets {@link RestApiApplication} with given name.
   *
   * @param name application name
   * @return optional of {@link RestApiApplication}
   */
  public Optional<RestApiApplication> getRestApiApplication(String name) {

    return restApiApplications
        .stream()
        .filter(restApiApplication -> restApiApplication.getName()
            .equalsIgnoreCase(name))
        .findFirst();
  }

  /**
   * Registers a restApiApplication.
   *
   * @param restApiApplication restApiApplication
   */
  public void registerRestApiApplication(RestApiApplication restApiApplication) {
    if (restApiApplication == null) {
      return;
    }
    this.restApiApplications.add(restApiApplication);
  }

  /**
   * Registers restApiApplications.
   *
   * @param restApiApplications restApiApplications
   */
  public void registerRestApiApplications(Iterable<RestApiApplication> restApiApplications) {
    if (restApiApplications == null) {
      return;
    }

    for (RestApiApplication restApiApplication
        : restApiApplications) {
      registerRestApiApplication(restApiApplication);
    }
  }

  /**
   * Removes previously registered restApiApplication by name.
   *
   * @param name application name
   */
  public void removeRestApiApplication(String name) {
    restApiApplications.removeIf(app -> app.getName().equalsIgnoreCase(name));
  }

}
