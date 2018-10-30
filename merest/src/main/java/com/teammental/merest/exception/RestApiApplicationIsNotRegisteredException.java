package com.teammental.merest.exception;

import com.teammental.mecore.stereotype.exception.Exception;

public class RestApiApplicationIsNotRegisteredException
    extends RuntimeException implements Exception {

  public RestApiApplicationIsNotRegisteredException() {
    super();
  }

  public RestApiApplicationIsNotRegisteredException(String applicationName) {
    super("A restApi application with name '" + applicationName + "' is not registered.");
  }
}
