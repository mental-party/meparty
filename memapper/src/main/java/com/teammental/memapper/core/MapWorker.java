package com.teammental.memapper.core;

import com.teammental.mehelper.AssertHelper;
import com.teammental.memapper.configuration.MapConfiguration;
import com.teammental.memapper.configuration.MapConfigurationRegistry;
import com.teammental.memapper.util.FieldUtil;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sa on 12/23/2017.
 */
public class MapWorker<S, T> {

  private static final Logger LOGGER = LoggerFactory.getLogger(MapWorker.class);

  MapConfiguration configuration;
  S source;
  T target;
  private int level;

  /**
   * Constructs a MapWorker instance.
   * @param source mapping source object.
   * @param target mapping target object.
   */
  public MapWorker(S source, T target) {
    init(source, target, 1);
  }

  private MapWorker(S source, T target, int level) {
    init(source, target, level);
  }

  private void init(S source, T target, int level) {
    AssertHelper.notNull(source, target);

    MapConfigurationRegistry registry = MapConfigurationRegistrySingleton.getSingleton();
    this.configuration = registry
        .getConfiguration(source.getClass(), target.getClass());
    this.source = source;
    this.target = target;
    this.level = level;
  }

  /**
   * Maps source to target.
   *
   * @return mapped target object
   */
  public T map() {

    doMap();
    return target;
  }

  /**
   * Does the mapping operation.
   */
  private void doMap() {

    Map<Field, Field> fieldMap = configuration.getFieldMap();
    for (Field sourceField :
        fieldMap.keySet()) {
      try {

        Optional<Method> getMethodOptional = FieldUtil.findGetMethod(sourceField);
        if (getMethodOptional.isPresent()) {
          try {
            Object val = getMethodOptional.get().invoke(source);

            // todo: depth level and recursive mapping

            Field targetField = fieldMap.get(sourceField);

            Optional<Method> setMethodOptional = FieldUtil.findSetMethod(targetField);

            if (setMethodOptional.isPresent()) {
              setMethodOptional.get().invoke(target, val);
            }
          } catch (InvocationTargetException ex) {

            LOGGER.error(ex.getLocalizedMessage());

            continue;
          }
        }

      } catch (IllegalAccessException ex) {
        LOGGER.error(ex.getLocalizedMessage());
      }
    }
  }
}
