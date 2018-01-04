package com.teammental.merest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.teammental.mecore.stereotype.controller.Controller;
import com.teammental.mecore.stereotype.controller.RestApi;
import com.teammental.mehelper.AssertHelper;
import com.teammental.mehelper.StringHelper;
import com.teammental.merest.exception.RestApiAnnotationIsMissingException;
import com.teammental.merest.exception.RestApiApplicationNameCannotBeNullOrEmpty;

public class RestApiFactory {


  /**
   * Returns Rest API implementation.
   * @param restApiClass type of Rest API
   * @param <T> Rest Api type
   * @return Rest API instance
   */
  public <T extends Controller> T getRestApi(Class<T> restApiClass) {
    AssertHelper.notNull(restApiClass);

    if (!restApiClass.isAnnotationPresent(RestApi.class)) {
      throw new RestApiAnnotationIsMissingException(restApiClass);
    }

    RestApi restApiAnnotation = restApiClass.getAnnotation(RestApi.class);
    String applicationName = restApiAnnotation.value();

    if (StringHelper.isNullOrEmpty(applicationName)) {
      throw new RestApiApplicationNameCannotBeNullOrEmpty(restApiClass);
    }

    InvocationHandler handler = new RestApiInvocationHandler();

    T restApi = (T) Proxy.newProxyInstance(restApiClass.getClassLoader(),
        new Class[]{restApiClass},
        handler);

    return restApi;
  }
}