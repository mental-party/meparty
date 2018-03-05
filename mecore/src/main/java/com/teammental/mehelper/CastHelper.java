package com.teammental.mehelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CastHelper {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(CastHelper.class);

  /**
   * Casts from String to given type.
   * Given type must be primitive or wrapper type.
   *
   * @param clazz type
   * @param value string value
   * @param <T>   casting type
   * @return casted value
   */
  @SuppressWarnings({"unchecked", "PMD"})
  public static <T> T castFromString(String value, Class<T> clazz) {

    if (clazz == null || value == null || value.isEmpty()
        || !PrimitiveHelper.isWrapperOrPrimitive(clazz)) {
      return null;
    }

    Class<?> wrapperClazz = PrimitiveHelper.getWrapperClass(clazz);

    try {
      T val;
      if (wrapperClazz.equals(Boolean.class)) {
        val = (T) castToBoolean(value);
      } else if (wrapperClazz.equals(Byte.class)) {
        val = (T) Byte.valueOf(value);
      } else if (wrapperClazz.equals(Short.class)) {
        val = (T) Short.valueOf(value);
      } else if (wrapperClazz.equals(Character.class)) {
        val = (T) Character.valueOf(value.charAt(0));
      } else if (wrapperClazz.equals(Integer.class)) {
        val = (T) Integer.valueOf(value);
      } else if (wrapperClazz.equals(Long.class)) {
        val = (T) Long.valueOf(value);
      } else if (wrapperClazz.equals(Float.class)) {
        val = (T) Float.valueOf(value);
      } else if (wrapperClazz.equals(Double.class)) {
        val = (T) Double.valueOf(value);
      } else {
        val = null;
      }

      return val;

    } catch (Exception ex) {
      LOGGER.error(ex.getLocalizedMessage());
      return null;
    }
  }

  private static Boolean castToBoolean(String value) {

    return value.equalsIgnoreCase("true")
        ? (Boolean) true
        : value.equalsIgnoreCase("false")
        ? false
        : null;
  }
}
