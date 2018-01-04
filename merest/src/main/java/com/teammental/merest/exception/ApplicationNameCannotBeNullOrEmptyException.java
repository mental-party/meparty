package com.teammental.merest.exception;

import com.teammental.mecore.stereotype.exception.Exception;

public class RestApiApplicationNameCannotBeNullOrEmpty extends RuntimeException implements Exception {

  public RestApiApplicationNameCannotBeNullOrEmpty(Class<?> clazz) {
    super("@RestApi annotation on" + clazz.getName() + " doesn't have a valid applicationName");
  }
}
