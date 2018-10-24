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
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("PMD.TooManyMethods")
public class MapConfigurationBuilder<S, T>
    implements ConfigurationBetween<S>,
    ConfigurationAnd<T>,
    ConfigurationMapField,
    ConfigurationMapWith {

  private static final Logger LOGGER
      = LoggerFactory.getLogger(MapConfigurationBuilder.class);

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
  private boolean hybridMappingEnabled;

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

    AssertHelper.notNull(sourceType);

    this.sourceType = sourceType;
    sourceFields = CommonMapUtil.getAllFields(sourceType, true);

    return this;
  }

  @Override
  public ConfigurationBetween enableHybridMapping() {
    this.hybridMappingEnabled = true;
    return this;
  }

  @Override
  public ConfigurationBetween disableHybridMapping() {
    this.hybridMappingEnabled = false;
    return this;
  }

  @Override
  public ConfigurationMapField and(Class<T> targetType) {

    AssertHelper.notNull(targetType);

    this.targetType = targetType;
    targetFields = CommonMapUtil.getAllFields(targetType, true);
    return this;
  }

  @Override
  public ConfigurationMapWith mapField(String sourceFieldName) {

    AssertHelper.notNull(sourceFieldName);

    if (sourceFields.stream().noneMatch(field -> field.getName().equals(sourceFieldName))) {
      throw new NoSuchFieldException(sourceFieldName, sourceType);
    }
    this.tempSourceFieldName = sourceFieldName;
    return this;
  }


  @Override
  public ConfigurationMapField with(String targetFieldName) {

    AssertHelper.notNull(targetFieldName);

    if (targetFields.stream().noneMatch(field -> field.getName().equals(targetFieldName))) {
      throw new NoSuchFieldException(targetFieldName, targetType);
    }

    addFieldMapItem(tempSourceFieldName, targetFieldName);

    this.tempSourceFieldName = null;
    return this;
  }

  private void addFieldMapItem(String sourceFieldName, String targetFieldName) {
    Field sourceField = sourceFields.stream()
        .filter(field -> field.getName().equals(sourceFieldName))
        .findFirst().get();


    Field targetField = targetFields.stream()
        .filter(field -> field.getName().equals(targetFieldName))
        .findFirst().get();

    addFieldMapItem(sourceField, targetField);
  }

  private void addFieldMapItem(Field sourceField, Field targetField) {

    if (!FieldUtil.hasPublicGetMethod(sourceField)) {
      throw new FieldIsNotAccessible(sourceField, false);
    }

    if (!FieldUtil.hasPublicSetMethod(targetField)) {
      throw new FieldIsNotAccessible(targetField, true);
    }


    if (!FieldUtil.isConvertable(sourceField, targetField)) {
      throw new FieldTypesAreNotAssignableException(sourceField, targetField);
    }

    fieldMap.put(sourceField, targetField);
  }


  @Override
  public MapConfiguration build() {

    if (this.hybridMappingEnabled) {
      addHybridMappings();
    }

    if (fieldMap.isEmpty()) {
      throw new IllegalArgumentException("You should add at least one field mapping");
    }

    return new MapConfiguration(fieldMap,
        sourceType,
        targetType,
        oneWayMapping,
        hybridMappingEnabled);
  }

  private void addHybridMappings() {

    for (Field sourceField
        : sourceFields) {

      if (fieldMap.containsKey(sourceField)) {
        continue;
      }

      Optional<Field> optionalTargetField
          = targetFields
          .stream()
          .filter(field -> field.getName()
              .equalsIgnoreCase(sourceField.getName()))
          .findFirst();

      if (optionalTargetField.isPresent()
          && !fieldMap.containsValue(optionalTargetField.get())) {
        try {
          addFieldMapItem(sourceField, optionalTargetField.get());

        } catch (Exception ex) {

          LOGGER.debug(ex.getLocalizedMessage());
        }
      }
    }
  }


}