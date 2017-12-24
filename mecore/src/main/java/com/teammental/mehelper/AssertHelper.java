package com.teammental.mehelper;

/**
 * Created by sa on 12/23/2017.
 */
public class AssertHelper {

  public static void NotNull(Object... objects) {

    for (Object object :
        objects) {
      if (object == null) {
        throw new IllegalArgumentException();
      }
    }
  }
}
