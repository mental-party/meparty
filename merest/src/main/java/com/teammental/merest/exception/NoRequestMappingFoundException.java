package com.teammental.merest.exception;

import java.lang.reflect.Method;

import com.teammental.mecore.stereotype.exception.Exception;

public class NoRequestMappingFoundException
    extends RuntimeException implements Exception {

  public NoRequestMappingFoundException(Method method) {
    super(method.getName() +" method is not annotated with @RequestMapping.");
  }
}
