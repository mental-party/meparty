package com.teammental.memapper.exception;

import com.teammental.mecore.stereotype.exception.Exception;

import java.lang.reflect.Field;

public class FieldTypesAreNotAssignableException extends RuntimeException implements Exception {

  /**
   * Constructor method.
   * Thrown if source field and target field are assignable to each other.
   *
   * @param sourceField source field
   * @param targetField target field
   */
  public FieldTypesAreNotAssignableException(Field sourceField, Field targetField) {

    super("Source field's type (" + sourceField.getType().getName() + ")"
        + " is not convertable with target field's type (" + targetField.getType().getName() + ")");
  }
}
