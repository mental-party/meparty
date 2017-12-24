package com.teammental.memapper.util;

import com.teammental.mehelper.AssertHelper;
import com.teammental.mehelper.StringHelper;
import com.teammental.memapper.util.mapping.CommonMapUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

/**
 * Created by erhan.karakaya on 5/11/2017.
 */
public class FieldUtil {

  /**
   * Checks if a field's type is boolean.
   *
   * @param field Field to be checked.
   * @return true if the field's type is boolean.
   */
  public static boolean isBoolean(final Field field) {

    AssertHelper.NotNull(field);

    Class<?> fieldType = field.getType();
    return fieldType.equals(boolean.class) || fieldType.equals(Boolean.class);
  }

  /**
   * Checks if two fields' types equals or are assignable from each other.
   *
   * @param firstField  first field
   * @param secondField second field
   * @return true if types of two field equals or are assignable.
   */
  public static boolean isConvertable(final Field firstField,
                                      final Field secondField) {

    AssertHelper.NotNull(firstField, secondField);

    return firstField.getType().equals(secondField.getType())
        || firstField.getType().isAssignableFrom(secondField.getType())
        || secondField.getType().isAssignableFrom(firstField.getType());
  }

  public static Optional<Field> getField(Class<?> clazz, String name) {

    AssertHelper.NotNull(clazz, name);

    List<Field> fields = CommonMapUtil.getAllFields(clazz);

    return fields.stream()
        .filter(field -> field.getName().equals(name))
        .findFirst();
  }

  /**
   * Returns set method of the field if any public set method found.
   *
   * @param field field
   * @return set method.
   */
  public static Optional<Method> findSetMethod(Field field) {

    AssertHelper.NotNull(field);

    Method method = null;
    Class<?> declaringClass = field.getDeclaringClass();
    try {
      Method _method = declaringClass.getMethod("set" + StringHelper.capitalizeFirstLetter(field.getName()), field.getType());

      if (_method != null) {
        method = _method;
      }
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }

    return Optional.ofNullable(method);
  }

  /**
   * Returns get method of the field if any public get method found.
   *
   * @param field field
   * @return get method.
   */
  public static Optional<Method> findGetMethod(Field field) {

    AssertHelper.NotNull(field);

    Method method = null;
    Class<?> declaringClass = field.getDeclaringClass();
    try {
      Method _method = declaringClass.getMethod("get" + StringHelper.capitalizeFirstLetter(field.getName()));

      if (_method != null  && _method.getReturnType().equals(field.getType())) {
        method = _method;
      }
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }

    return Optional.ofNullable(method);
  }
}
