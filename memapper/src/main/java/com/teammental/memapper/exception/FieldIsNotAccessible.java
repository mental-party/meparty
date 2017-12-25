package com.teammental.memapper.exception;

import com.teammental.mecore.stereotype.exception.Exception;

import java.lang.reflect.Field;

/**
 * Created by sa on 12/23/2017.
 */
public class FieldIsNotAccessible extends RuntimeException implements Exception {

  /**
   * Constructor method.
   *
   * @param field field object which is not accessible.
   * @param isSet if true, field doesn't have a public setter method;
   *              if false, field doesn't have a public getter method.
   */
  public FieldIsNotAccessible(Field field, boolean isSet) {

    super("Field '" + field.getName() + "' is not accessible. "
        + "There is no public " + (isSet ? "set" : "get") + field.getName()
        + "() method in the field's declaring type.");
  }
}
