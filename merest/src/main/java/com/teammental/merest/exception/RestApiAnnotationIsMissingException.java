package com.teammental.merest.exception;

import com.teammental.mecore.stereotype.exception.Exception;

public class RestApiAnnotationIsMissingException extends RuntimeException
    implements Exception {

  public RestApiAnnotationIsMissingException(Class<?> clazz) {
    super(clazz.getName() + " doesn't have @RestApi annotation.");
  }
}
