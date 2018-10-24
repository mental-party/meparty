package com.teammental.memapper.configuration;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MapConfiguration {
  private Map<Field, Field> fieldMap;
  private Class<?> sourceType;
  private Class<?> targetType;
  private boolean oneWayMapping;
  private boolean hybridMappingEnabled;

  boolean isOneWayMapping() {
    return oneWayMapping;
  }

  MapConfiguration(Map<Field, Field> fieldMap,
                   Class<?> sourceType,
                   Class<?> targetType,
                   boolean oneWayMapping,
                   boolean hybridMappingEnabled) {

    this.fieldMap = fieldMap;
    this.sourceType = sourceType;
    this.targetType = targetType;
    this.oneWayMapping = oneWayMapping;
    this.hybridMappingEnabled = hybridMappingEnabled;
  }

  public Map<Field, Field> getFieldMap() {
    return fieldMap;
  }

  public Class<?> getSourceType() {
    return sourceType;
  }


  public Class<?> getTargetType() {
    return targetType;
  }

  public boolean isHybridMappingEnabled() {
    return hybridMappingEnabled;
  }

  @Override
  public int hashCode() {
    return sourceType.hashCode() ^ targetType.hashCode();
  }

  @Override
  public boolean equals(Object obj) {

    MapConfiguration other = (MapConfiguration) obj;

    return this.targetType != null && this.sourceType != null
        && other.targetType != null && other.sourceType != null
        && this.targetType.equals(other.targetType)
        && this.sourceType.equals(other.sourceType);
  }

  MapConfiguration reverse() {

    Map<Field, Field> reverseMap = new HashMap<>();

    for (Field key :
        this.fieldMap.keySet()) {
      reverseMap.put(this.fieldMap.get(key), key);
    }

    Class<?> reverseSourceType = this.targetType;
    Class<?> reverseTargetType = this.sourceType;

    MapConfiguration configuration =
        new MapConfiguration(reverseMap,
            reverseSourceType,
            reverseTargetType,
            this.isOneWayMapping(),
            this.isHybridMappingEnabled());

    return configuration;
  }

}
