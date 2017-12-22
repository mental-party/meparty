package com.teammental.memapper.exception;

import com.teammental.mecore.stereotype.exception.Exception;

public class NoSuchFieldException extends RuntimeException implements Exception {
  public NoSuchFieldException(String fieldName, Class<?> clazz) {
    super("There is no field in " + clazz.getName() + " as " + fieldName);
  }
}
