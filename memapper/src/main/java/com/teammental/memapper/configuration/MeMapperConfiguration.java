package com.teammental.memapper.configuration;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MeMapperConfiguration {
  private Map<Field, Field> fieldMap;
  private Class<?> sourceType;
  private Class<?> targetType;
  private boolean oneWayMapping;

  boolean isOneWayMapping() {
    return oneWayMapping;
  }

  MeMapperConfiguration(Map<Field, Field> fieldMap,
                        Class<?> sourceType,
                        Class<?> targetType,
                        boolean oneWayMapping) {

    this.fieldMap = fieldMap;
    this.sourceType = sourceType;
    this.targetType = targetType;
    this.oneWayMapping = oneWayMapping;
  }

  public Map<Field, Field> getFieldMap() {
    return fieldMap;
  }

  public Field getFieldMap(Field key) {
    return fieldMap.get(key);
  }

  public Class<?> getSourceType() {
    return sourceType;
  }


  public Class<?> getTargetType() {
    return targetType;
  }


  @Override
  public int hashCode() {
    return sourceType.hashCode() ^ targetType.hashCode();
  }

  @Override
  public boolean equals(Object obj) {

    MeMapperConfiguration other = (MeMapperConfiguration) obj;

    return this.targetType != null && this.sourceType != null
        && other.targetType != null && other.sourceType != null
        && this.targetType.equals(other.targetType)
        && this.sourceType.equals(other.sourceType);
  }

  MeMapperConfiguration reverse() {

    Map<Field, Field> reverseMap = new HashMap<>();

    for (Field key :
        this.fieldMap.keySet()) {
      reverseMap.put(this.fieldMap.get(key), key);
    }

    Class<?> reverseSourceType = this.targetType;
    Class<?> reverseTargetType = this.sourceType;

    MeMapperConfiguration configuration =
        new MeMapperConfiguration(reverseMap,
            reverseSourceType, reverseTargetType, this.isOneWayMapping());

    return configuration;
  }

}
