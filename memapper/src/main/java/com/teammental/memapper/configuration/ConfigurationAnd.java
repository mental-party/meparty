package com.teammental.memapper.configuration;

public interface ConfigurationAnd<T> {
  ConfigurationMapField and(Class<T> targetType);
}
