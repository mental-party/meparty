package com.teammental.merest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.teammental.mecore.stereotype.controller.RestApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

class RestApiInvocationHandler implements InvocationHandler {

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

    RestApi restApiAnnotation = proxy.getClass().getAnnotation(RestApi.class);
    String applicationName = restApiAnnotation.value();

    String applicationRootUrl = getApplicationUrl(applicationName);

    RequestMethod requestMethod = extractRequestMethod(method);


    return null;
  }

  private String getApplicationUrl(String applicationName) {
    return applicationName;
  }

  private RequestMethod extractRequestMethod(Method method) {
    RequestMapping requestMappingAnnotation = method.getAnnotation(RequestMapping.class);
    RequestMethod[] methods = requestMappingAnnotation.method();
    return methods.length == 0 ? RequestMethod.GET : methods[0];
  }
}
