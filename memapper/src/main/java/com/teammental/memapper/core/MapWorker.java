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

/**
 * Created by sa on 12/23/2017.
 */
public class MapWorker<S, T> {

  MapConfiguration configuration;
  S source;
  T target;

  /**
   * Constructs a MapWorker instance.
   * @param source mapping source object.
   * @param target mapping target object.
   */
  public MapWorker(S source, T target) {

    AssertHelper.notNull(source, target);

    MapConfigurationRegistry registry = MapConfigurationRegistrySingleton.getSingleton();
    this.configuration = registry
        .getConfiguration(source.getClass(), target.getClass());
    this.source = source;
    this.target = target;
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

            Field targetField = fieldMap.get(sourceField);

            Optional<Method> setMethodOptional = FieldUtil.findSetMethod(targetField);

            if (setMethodOptional.isPresent()) {
              setMethodOptional.get().invoke(target, val);
            }
          } catch (InvocationTargetException e) {
            e.printStackTrace();
            continue;
          }
        }

      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  }
}
