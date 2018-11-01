package com.teammental.mecore.stereotype.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RestApiProxy {

  /**
   * Application name.
   * Must be same with the Spring application name.
   *
   * @return application name
   */
  @AliasFor("applicationName")
  String value() default "";

  /**
   * Application name.
   *
   * @return application name
   * @see #value() .
   */
  @AliasFor("value")
  String applicationName() default "";

}
