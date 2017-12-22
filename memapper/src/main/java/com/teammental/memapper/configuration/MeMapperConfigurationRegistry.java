package com.teammental.memapper.configuration;

import java.util.LinkedHashSet;
import java.util.Set;

public class MeMapperConfigurationRegistry {

  private Set<MeMapperConfiguration> registry;

  public MeMapperConfigurationRegistry() {
    registry = new LinkedHashSet<>();
  }

  /**
   * Registers new configuration.
   *
   * @param configuration configuration item.
   */
  public void register(MeMapperConfiguration configuration) {

    if (configuration == null) {
      throw new IllegalArgumentException();
    }

    registry.add(configuration);
    if (!configuration.isOneWayMapping()) {
      MeMapperConfiguration reverseConfiguration = configuration.reverse();
      registry.add(reverseConfiguration);
    }
  }
}
