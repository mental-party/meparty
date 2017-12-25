package com.teammental.mehelper;

/**
 * Created by sa on 12/23/2017.
 */
public class AssertHelper {

  /**
   * Asserts the given objects by checking if any of them is null.
   * Throws an IllegalArgumentException if any of the given objects is null.
   *
   * @param objects objects to be asserted
   */
  public static void notNull(Object... objects) {

    for (Object object :
        objects) {
      if (object == null) {
        throw new IllegalArgumentException();
      }
    }
  }
}
