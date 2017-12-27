package com.teammental.memapper.configuration;

import com.teammental.mehelper.AssertHelper;
import com.teammental.memapper.util.FieldUtil;
import com.teammental.memapper.util.mapping.CommonMapUtil;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class MapConfigurationRegistry {

  private Set<MapConfiguration> registry;

  public MapConfigurationRegistry() {

    registry = new LinkedHashSet<>();
  }

  /**
   * Merges this with another MapConfigurationRegistry object.
   * @param mapConfigurationRegistry other MapConfigurationRegistry
   */
  public void merge(MapConfigurationRegistry mapConfigurationRegistry) {

    AssertHelper.notNull(mapConfigurationRegistry);

    for (MapConfiguration configuration:
        mapConfigurationRegistry.registry) {
      this.registry.add(configuration);
    }
  }

  /**
   * Registers new configuration.
   *
   * @param configuration configuration item.
   */
  public void register(MapConfiguration configuration) {

    AssertHelper.notNull(configuration);

    registry.add(configuration);
    if (!configuration.isOneWayMapping()) {
      MapConfiguration reverseConfiguration = configuration.reverse();
      registry.add(reverseConfiguration);
    }
  }

  /**
   * Gets mapping configuration between two types.
   * If no custom configuration found, creates a default
   * configuration set by name-to-name
   * @param sourceType source type of mapping configuration.
   * @param targetType target type of mapping configuration.
   * @return a MapConfiguration item.
   */
  public MapConfiguration getConfiguration(Class<?> sourceType,
                                           Class<?> targetType) {

    AssertHelper.notNull(sourceType, targetType);

    MapConfiguration configuration = registry.stream()
        .filter(mapConfiguration ->
            mapConfiguration.getSourceType().equals(sourceType)
                && mapConfiguration.getTargetType().equals(targetType))
        .findFirst()
        .orElse(createDefaultConfiguration(sourceType, targetType));

    return configuration;
  }

  private MapConfiguration createDefaultConfiguration(Class<?> sourceType,
                                                      Class<?> targetType) {

    List<Field> sourceFields = CommonMapUtil.getAllFields(sourceType);
    List<Field> targetFields = CommonMapUtil.getAllFields(targetType);
    Map<Field, Field> fieldMap = new HashMap<>();

    for (Field sourceField :
        sourceFields) {

      Optional<Field> targetFieldOptional = targetFields.stream()
          .filter(field -> field.getName().equals(sourceField.getName())).findFirst();

      if (targetFieldOptional.isPresent()
          && FieldUtil.isConvertable(sourceField, targetFieldOptional.get())) {
        fieldMap.put(sourceField, targetFieldOptional.get());
      }
    }

    MapConfiguration mapConfiguration =
        new MapConfiguration(fieldMap, sourceType, targetType, false);

    registry.add(mapConfiguration);

    return mapConfiguration;
  }
}
