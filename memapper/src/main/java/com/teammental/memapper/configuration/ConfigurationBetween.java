package com.teammental.memapper.configuration;

public interface ConfigurationBetween<S> {
  ConfigurationAnd between(Class<S> sourceType);
}
