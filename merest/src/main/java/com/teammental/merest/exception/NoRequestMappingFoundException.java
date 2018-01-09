package com.teammental.merest.exception;

import com.teammental.mecore.stereotype.exception.Exception;

import java.lang.reflect.Method;

public class NoRequestMappingFoundException
    extends RuntimeException implements Exception {

  public NoRequestMappingFoundException(Method method) {
    super(method.getName() + " method is not annotated with @RequestMapping.");
  }
}
