package com.teammental.memapper.configuration;

import com.teammental.mehelper.AssertHelper;
import com.teammental.memapper.exception.FieldIsNotAccessible;
import com.teammental.memapper.exception.FieldTypesAreNotAssignableException;
import com.teammental.memapper.exception.NoSuchFieldException;
import com.teammental.memapper.util.FieldUtil;
import com.teammental.memapper.util.mapping.CommonMapUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapConfigurationBuilder<S, T>
    implements ConfigurationBetween<S>,
    ConfigurationAnd<T>,
    ConfigurationMapField,
    ConfigurationMapWith {

  private MapConfigurationBuilder(boolean oneWayMapping) {

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

  public static ConfigurationBetween oneWayMapping() {

    return new MapConfigurationBuilder(true);
  }

  public static ConfigurationBetween twoWayMapping() {

    return new MapConfigurationBuilder(false);
  }

  @Override
  public ConfigurationAnd between(Class<S> sourceType) {

    AssertHelper.NotNull(sourceType);

    this.sourceType = sourceType;
    sourceFields = CommonMapUtil.getAllFields(sourceType, true);

    return this;
  }

  @Override
  public ConfigurationMapField and(Class<T> targetType) {

    AssertHelper.NotNull(targetType);

    this.targetType = targetType;
    targetFields = CommonMapUtil.getAllFields(targetType, true);
    return this;
  }

  @Override
  public ConfigurationMapWith mapField(String sourceFieldName) {

    AssertHelper.NotNull(sourceFieldName);

    if (!sourceFields.stream().anyMatch(field -> field.getName().equals(sourceFieldName))) {
      throw new NoSuchFieldException(sourceFieldName, sourceType);
    }
    this.tempSourceFieldName = sourceFieldName;
    return this;
  }


  @Override
  public ConfigurationMapField with(String targetFieldName) {

    AssertHelper.NotNull(targetFieldName);

    if (!targetFields.stream().anyMatch(field -> field.getName().equals(targetFieldName))) {
      throw new NoSuchFieldException(targetFieldName, targetType);
    }

    Field sourceField = sourceFields.stream()
        .filter(field -> field.getName().equals(tempSourceFieldName))
        .findFirst().get();

    if (!FieldUtil.hasPublicGetMethod(sourceField)) {
      throw new FieldIsNotAccessible(sourceField, false);
    }

    Field targetField = targetFields.stream()
        .filter(field -> field.getName().equals(targetFieldName))
        .findFirst().get();

    if (!FieldUtil.hasPublicSetMethod(targetField)) {
      throw new FieldIsNotAccessible(targetField, true);
    }


    if (!FieldUtil.isConvertable(sourceField, targetField)) {
      throw new FieldTypesAreNotAssignableException(sourceField, targetField);
    }


    fieldMap.put(sourceField, targetField);

    this.tempSourceFieldName = null;
    return this;
  }


  @Override
  public MapConfiguration build() {

    if (fieldMap.isEmpty()) {
      throw new IllegalArgumentException("You should add at least one field mapping");
    }

    return new MapConfiguration(fieldMap, sourceType, targetType, oneWayMapping);
  }


}