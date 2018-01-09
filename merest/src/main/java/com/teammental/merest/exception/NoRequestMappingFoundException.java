package com.teammental.merest.exception;

import com.teammental.mecore.stereotype.exception.Exception;

import java.lang.reflect.AnnotatedElement;

public class NoRequestMappingFoundException
    extends RuntimeException implements Exception {

  public NoRequestMappingFoundException(AnnotatedElement element) {
    super(element.getClass().getName() + " is not annotated with @RequestMapping.");
  }
}
