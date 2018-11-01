package com.teammental.merest.exception;

import com.teammental.mecore.stereotype.exception.Exception;

public class RestApiProxyAnnotationIsMissingException extends RuntimeException
    implements Exception {

  public RestApiProxyAnnotationIsMissingException(Class<?> clazz) {
    super(clazz.getName() + " doesn't have @RestApiProxy annotation.");
  }
}
