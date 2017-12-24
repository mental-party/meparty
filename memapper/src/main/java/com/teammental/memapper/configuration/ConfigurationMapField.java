package com.teammental.memapper.configuration;

public interface ConfigurationMapField {

  ConfigurationMapWith mapField(String sourceFieldName);

  MapConfiguration build();
}
