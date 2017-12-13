package com.teammental.memapper.util.mapping;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * author @er-han on 3/30/2017.
 */
public class CommonMapUtil {

  /**
   * Gets all public, private, protected fields of the given type.
   * If the given type has superclass, gets it's fields too using recursive call of self.
   *
   * @param type The type which's fields will be returned.
   * @return List of the fields of the given type.
   */
  public static List<Field> getAllFields(Class<?> type) {
    return getAllFields(type, true);
  }

  /**
   * Gets all public, private, protected fields of the given type.
   *
   * @param type               The type which's fields will be returned.
   * @param includeSuperFields if false, fields of superclass will not be included;
   *                           if true, fields of superclass will be included.
   * @return List of the fields of the given type.
   */
  public static List<Field> getAllFields(Class<?> type, boolean includeSuperFields) {
    List<Field> fields = new ArrayList<>();
    if (includeSuperFields && type.getSuperclass() != null) {
      List<Field> fieldsOfSuper = getAllFields(type.getSuperclass());
      fields.addAll(fieldsOfSuper);
    }

    List<Field> fieldsOfCurrent = Arrays.stream(type.getDeclaredFields())
        .filter(field -> !field.isSynthetic())
        .collect(Collectors.toList());

    fields = fields.stream()
        .filter(field -> !fieldsOfCurrent.stream()
            .anyMatch(superField -> superField.getName().equals(field.getName())))
        .collect(Collectors.toList());
    fields.addAll(fieldsOfCurrent);

    return fields;
  }


  /**
   * Extracts a map of fields in given T object.
   * If given object type has a super class,
   * the fields derived from the super class is extracted too.
   *
   * @param source The object which's fields are wanted to be extracted.
   * @param <T>    Generic type of source object.
   * @return a Map object which contains extracted fields names and values from the given object
   * @throws IllegalAccessException throws this when can't get one of the field's value of source.
   */
  public static <T> Map<String, Object> getFieldsMap(final T source)
      throws IllegalAccessException {
    return getFieldsMap(source, true);
  }

  /**
   * Extracts a map of fields in given T object.
   *
   * @param source             The object which's fields are wanted to be extracted.
   * @param includeSuperFields if false, fields of superclass will not be included;
   *                           if true, fields of superclass will be included.
   * @param <T>                Generic type of source object.
   * @return a Map object which contains extracted fields names and values from the given object
   * @throws IllegalAccessException throws this when can't get one of the field's value of source.
   */
  public static <T> Map<String, Object> getFieldsMap(final T source, boolean includeSuperFields)
      throws IllegalAccessException {
    final Map<String, Object> map = new HashMap<>();
    final List<Field> fields = getAllFields(source.getClass(), includeSuperFields);
    for (final Field field : fields) {
      final boolean isFieldAccessible = field.isAccessible();
      field.setAccessible(true);
      map.put(field.getName(), field.get(source));
      field.setAccessible(isFieldAccessible);
    }
    return map;
  }

}
