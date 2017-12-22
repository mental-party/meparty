package com.teammental.memapper.configuration;

import com.teammental.memapper.exception.FieldTypesNotConvertableException;
import com.teammental.memapper.exception.NoSuchFieldException;
import com.teammental.memapper.util.FieldUtil;
import com.teammental.memapper.util.mapping.CommonMapUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeMapperConfigurationBuilder<S, T> implements ConfigurationBetween<S>,
    ConfigurationAnd<T>,
    ConfigurationMapField,
    ConfigurationMapWith {

  private MeMapperConfigurationBuilder(boolean oneWayMapping) {
    fieldMap = new HashMap<>();
    this.oneWayMapping = oneWayMapping;
  }

  private Class<S> sourceType;
  private Class<T> targetType;
  private Map<Field, Field> fieldMap;
  private boolean oneWayMapping;
  private String tempSourceFieldName;
  private List<Field> sourceFields;
  private List<Field> targetFields;

  public boolean isOneWayMapping() {
    return oneWayMapping;
  }

  public static ConfigurationBetween onaWayMapping() {
    return new MeMapperConfigurationBuilder(true);
  }

  public static ConfigurationBetween twoWayMapping() {
    return new MeMapperConfigurationBuilder(false);
  }

  @Override
  public ConfigurationAnd between(Class<S> sourceType) {
    this.sourceType = sourceType;
    sourceFields = CommonMapUtil.getAllFields(sourceType, true);
    return this;
  }

  @Override
  public ConfigurationMapField and(Class<T> targetType) {
    this.targetType = targetType;
    targetFields = CommonMapUtil.getAllFields(targetType, true);
    return this;
  }

  @Override
  public ConfigurationMapWith mapField(String sourceFieldName) {
    if (!sourceFields.stream().anyMatch(field -> field.getName().equals(sourceFieldName))) {
      throw new NoSuchFieldException(sourceFieldName, sourceType);
    }
    this.tempSourceFieldName = sourceFieldName;
    return this;
  }

  @Override
  public MeMapperConfiguration build() {
    return new MeMapperConfiguration(fieldMap, sourceType, targetType, oneWayMapping);
  }

  @Override
  public ConfigurationMapField with(String targetFieldName) {
    if (!targetFields.stream().anyMatch(field -> field.getName().equals(targetFieldName))) {
      throw new NoSuchFieldException(targetFieldName, targetType);
    }

    Field sourceField = sourceFields.stream()
        .filter(field -> field.getName().equals(tempSourceFieldName))
        .findFirst().get();

    Field targetField = targetFields.stream()
        .filter(field -> field.getName().equals(targetFieldName))
        .findFirst().get();


    if (!FieldUtil.isConvertable(sourceField, targetField)) {
      throw new FieldTypesNotConvertableException(sourceField, targetField);
    }

    fieldMap.put(sourceField, targetField);
    this.tempSourceFieldName = null;
    return this;
  }

}