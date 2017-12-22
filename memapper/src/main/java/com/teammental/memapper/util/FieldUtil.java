package com.teammental.memapper.util;

import java.lang.reflect.Field;

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
    if (field == null) {
      throw new IllegalArgumentException();
    }
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
    if (firstField == null || secondField == null) {
      throw new IllegalArgumentException();
    }

    return firstField.getType().equals(secondField.getType())
        || firstField.getType().isAssignableFrom(secondField.getType())
        || secondField.getType().isAssignableFrom(firstField.getType());
  }
}
