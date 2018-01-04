package com.teammental.merest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.teammental.mecore.stereotype.controller.RestApi;
import com.teammental.mecore.stereotype.dto.Dto;
import com.teammental.merest.exception.NoRequestMappingFoundException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

class RestApiProxyInvocationHandler implements InvocationHandler {

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
        } else {
          urlVariables.add(arg);
        }
      }
    }

    Class<?> returnType = extractReturnType(method.getGenericReturnType());


    return restTemplate.exchange(url, httpMethod, httpEntity, returnType, urlVariables.toArray());
  }

  Class<?> extractReturnType(Type genericReturnType) {
    Class<?> returnType;
    if (genericReturnType instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) genericReturnType;
      Type[] types = parameterizedType.getActualTypeArguments();
      if (types.length == 1) {
        Type type = types[0];
        returnType = extractReturnType(type);
      } else {
        returnType = Object.class;
      }
    } else {
      returnType = (Class<?>) genericReturnType;
    }

    return returnType;
  }

  String extractApiUrl(Object proxy, Method method) {
    RestApi restApiAnnotation = AnnotationUtils.findAnnotation(proxy.getClass(), RestApi.class);
    String applicationName = restApiAnnotation.value();

    String applicationRootUrl = getApplicationUrl(applicationName);

    String url = applicationRootUrl;

    String classLevelUrl = extractClassLevelMappingUrl(method.getDeclaringClass());

    String methodLevelUrl = extractMethodLevelMappingUrl(method);

    return url + classLevelUrl + methodLevelUrl;
  }


  String getApplicationUrl(String applicationName) {
    return ApplicationExplorer.getApplicationUrl(applicationName);
  }

  String extractMethodLevelMappingUrl(Method method) {

    RequestMapping requestMapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);

    if (requestMapping == null || requestMapping.path().length == 0) {
      return "";
    } else {
      return requestMapping.value()[0];
    }
  }

  String extractClassLevelMappingUrl(Class<?> clazz) {
    RequestMapping requestMapping = AnnotationUtils.findAnnotation(clazz, RequestMapping.class);

    if (requestMapping == null || requestMapping.path().length == 0) {
      return "";
    } else {
      return requestMapping.value()[0];
    }
  }

  /**
   * Searches {@link org.springframework.web.bind.annotation.RequestMapping RequestMapping}
   * annotation on the given method argument and extracts
   * If RequestMapping annotation is not found, NoRequestMappingFoundException is thrown.
   * {@link org.springframework.http.HttpMethod HttpMethod} type equivalent to
   * {@link org.springframework.web.bind.annotation.RequestMethod RequestMethod} type
   *
   * @param method Method to be examined.
   * @return {@link org.springframework.http.HttpMethod HttpMethod} type. Default is HttpMethod.Get
   */
  HttpMethod extractHttpMethod(Method method) {
    RequestMapping requestMappingAnnotation = method.getAnnotation(RequestMapping.class);
    RequestMethod requestMethod;

    if (requestMappingAnnotation == null) {
      if (method.isAnnotationPresent(GetMapping.class)) {
        requestMethod = RequestMethod.GET;
      } else if (method.isAnnotationPresent(PostMapping.class)) {
        requestMethod = RequestMethod.POST;
      } else if (method.isAnnotationPresent(PutMapping.class)) {
        requestMethod = RequestMethod.PUT;
      } else if (method.isAnnotationPresent(DeleteMapping.class)) {
        requestMethod = RequestMethod.DELETE;
      } else if (method.isAnnotationPresent(PatchMapping.class)) {
        requestMethod = RequestMethod.PATCH;
      } else {
        throw new NoRequestMappingFoundException(method);
      }
    } else {
      RequestMethod[] methods = requestMappingAnnotation.method();
      requestMethod = methods.length == 0 ? RequestMethod.GET : methods[0];
    }

    HttpMethod httpMethod = HttpMethod.resolve(requestMethod.name());
    if (httpMethod == null) {
      httpMethod = HttpMethod.GET;
    }
    return httpMethod;
  }
}
