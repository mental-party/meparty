package com.teammental.mehelper;

/**
 * DtoHelper.
 */
public class DtoHelper {

  /**
   * Controls if dto has any value.
   * @param params Object
   * @return boolean
   */
  public static boolean isDtoEmpty(Object... params) {
    boolean result = true;
    for (Object object : params) {
      if (!isFieldNullOrEmpty(object)) {
        result = false;
        break;
      }
    }
    return result;
  }

  /**
   * Controls field is null or empty.
   * @param object Object
   * @return boolean
   */
  public static boolean isFieldNullOrEmpty(Object object) {
    return object == null || object.toString().isEmpty();
  }

}
