package com.teammental.memapper.configuration;

public interface ConfigurationDepthLevel<S> extends ConfigurationBetween<S> {

  ConfigurationBetween<S> depthLevel(int level);
}
