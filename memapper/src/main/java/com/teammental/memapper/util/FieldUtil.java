package com.teammental.memapper.util;

import java.lang.reflect.Field;

/**
 * Created by erhan.karakaya on 5/11/2017.
 */
public class FieldUtil {

  /**
   * Checks if a field's type is boolean.
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
}
