package com.teammental.mecore.stereotype.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Deprecated since 1.3.
 *
 * @see RestApiProxy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Deprecated
public @interface RestApi {

  /**
   * Application name.
   * Must be same with the Spring application name.
   *
   * @return string
   */
  String value() default "";

}
