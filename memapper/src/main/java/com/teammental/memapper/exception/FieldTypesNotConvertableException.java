package com.teammental.memapper.exception;

import com.teammental.mecore.stereotype.exception.Exception;

import java.lang.reflect.Field;

public class FieldTypesNotConvertableException extends RuntimeException implements Exception {
  public FieldTypesNotConvertableException(Field sourceField, Field targetField) {

    super("Source field's type (" + sourceField.getType().getName() + ")"
        + " is not convertable with target field's type (" + targetField.getType().getName() + ")");
  }
}
