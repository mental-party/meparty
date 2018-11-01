package com.teammental.merest.util;

import com.teammental.mecore.stereotype.controller.RestApi;
import com.teammental.mecore.stereotype.controller.RestApiProxy;
import org.springframework.core.annotation.AnnotationUtils;

public class RestApiProxyUtil {

  /**
   * Extract application name attribute from @RestApiProxy annotated type.
   *
   * @param type type with @RestApiProxy annotation.
   * @return application name.
   */
  public static String getRestApiApplicationName(Class<?> type) {
    RestApiProxy restApiProxyAnnotation
        = AnnotationUtils.findAnnotation(type, RestApiProxy.class);
    if (restApiProxyAnnotation != null) {
      return restApiProxyAnnotation.applicationName();
    }
    RestApi restApiAnnotation
        = AnnotationUtils.findAnnotation(type, RestApi.class);

    if (restApiAnnotation != null) {
      return restApiAnnotation.value();
    }
    return null;
  }
}
