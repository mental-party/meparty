package com.teammental.merest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.teammental.mecore.stereotype.controller.RestApi;
import com.teammental.mecore.stereotype.dto.Dto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

class RestApiInvocationHandler implements InvocationHandler {

  RestTemplate restTemplate = new RestTemplate();

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

    String url = extractApiUrl(proxy, method);

    HttpMethod httpMethod = extractHttpMethod(method);

    HttpEntity httpEntity = HttpEntity.EMPTY;
    List<Object> urlVariables = new ArrayList<>();

    if (args != null && args.length > 0) {
      for (int i = 0; i < args.length; i++) {
        Object arg = args[i];

        if (arg instanceof Dto) {
          httpEntity = new HttpEntity(arg);
        }
      }
    }

//   ResponseEntity responseEntity = restTemplate.exchange(url, httpMethod)


    return null;
  }

  private String getApplicationUrl(String applicationName) {
    return applicationName;
  }

  private String extractApiUrl(Object proxy, Method method) {
    RestApi restApiAnnotation = proxy.getClass().getAnnotation(RestApi.class);
    String applicationName = restApiAnnotation.value();

    String applicationRootUrl = getApplicationUrl(applicationName);

    String url = applicationRootUrl;

    return url;
  }

  private HttpMethod extractHttpMethod(Method method) {
    RequestMapping requestMappingAnnotation = method.getAnnotation(RequestMapping.class);
    RequestMethod[] methods = requestMappingAnnotation.method();
    RequestMethod requestMethod = methods.length == 0 ? RequestMethod.GET : methods[0];
    HttpMethod httpMethod = HttpMethod.resolve(requestMethod.name());
    if (httpMethod == null) {
      httpMethod = HttpMethod.GET;
    }
    return httpMethod;
  }
}
