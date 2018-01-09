package com.teammental.merest.exception;

import com.teammental.mecore.stereotype.exception.Exception;

public class ApplicationNameCannotBeNullOrEmptyException
    extends RuntimeException
    implements Exception {

  public ApplicationNameCannotBeNullOrEmptyException(Class<?> clazz) {
    super("@RestApi annotation on " + clazz.getName() + " doesn't have a valid applicationName");
  }
}
