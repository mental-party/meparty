package com.teammental.merest;

import static org.junit.Assert.*;

import com.teammental.medto.FilterDto;
import com.teammental.merest.testapp.BaseTitleRestApi;
import com.teammental.merest.testapp.TestDto;
import com.teammental.merest.testapp.TestRestApi;
import java.lang.reflect.Method;

import com.teammental.merest.exception.NoRequestMappingFoundException;
import com.teammental.merest.testapp.Config;
import com.teammental.merest.testapp.SuperRestApi_Annotations;
import com.teammental.merest.testapp.ChildRestApi_Annotations;
import java.lang.reflect.Type;
import org.junit.Test;
import org.springframework.core.GenericTypeResolver;
import org.springframework.http.HttpMethod;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

public class RestApiProxyInvocationHandlerTest {

  public static class HttpMethods {

    @Test
    public void requestMapping_shouldExtractHttpMethod() throws NoSuchMethodException {
      Method method = ChildRestApi_Annotations.class.getMethod("requestMapping_post");

      RestApiProxyInvocationHandler handler = new RestApiProxyInvocationHandler(TestRestApi.class);
      HttpMethod httpMethod = handler.extractMapping(method).getHttpMethod();

      assertEquals(httpMethod, HttpMethod.POST);
    }

    @Test
    public void requestMapping_shouldExtract_HttpMethodGet_whenNoRequestMethodIsSet() throws NoSuchMethodException {
      Method method = ChildRestApi_Annotations.class.getMethod("requestMapping_noRequestMethodIsSet");

      RestApiProxyInvocationHandler handler = new RestApiProxyInvocationHandler(TestRestApi.class);
      HttpMethod httpMethod = handler.extractMapping(method).getHttpMethod();

      assertEquals(httpMethod, HttpMethod.GET);
    }

    @Test(expected = NoRequestMappingFoundException.class)
    public void shouldThrowException_whenNoRequestMappingFound() throws NoSuchMethodException {
      Method method = ChildRestApi_Annotations.class.getMethod("noRequestMappingAnnotation");

      RestApiProxyInvocationHandler handler = new RestApiProxyInvocationHandler(TestRestApi.class);
      HttpMethod httpMethod = handler.extractMapping(method).getHttpMethod();
    }

    @Test
    public void getMapping_shouldExtract_HttpMethodGet() throws NoSuchMethodException {
      Method method = ChildRestApi_Annotations.class.getMethod("getMapping");

      RestApiProxyInvocationHandler handler = new RestApiProxyInvocationHandler(TestRestApi.class);
      HttpMethod httpMethod = handler.extractMapping(method).getHttpMethod();

      assertEquals(httpMethod, HttpMethod.GET);
    }

    @Test
    public void postMapping_shouldExtract_HttpMethodPost() throws NoSuchMethodException {
      Method method = ChildRestApi_Annotations.class.getMethod("postMapping");

      RestApiProxyInvocationHandler handler = new RestApiProxyInvocationHandler(TestRestApi.class);
      HttpMethod httpMethod = handler.extractMapping(method).getHttpMethod();

      assertEquals(httpMethod, HttpMethod.POST);
    }

    @Test
    public void putMapping_shouldExtract_HttpMethodPut() throws NoSuchMethodException {
      Method method = ChildRestApi_Annotations.class.getMethod("putMapping");

      RestApiProxyInvocationHandler handler = new RestApiProxyInvocationHandler(TestRestApi.class);
      HttpMethod httpMethod = handler.extractMapping(method).getHttpMethod();

      assertEquals(httpMethod, HttpMethod.PUT);
    }

    @Test
    public void deleteMapping_shouldExtract_HttpMethodDelete() throws NoSuchMethodException {
      Method method = ChildRestApi_Annotations.class.getMethod("deleteMapping");

      RestApiProxyInvocationHandler handler = new RestApiProxyInvocationHandler(TestRestApi.class);
      HttpMethod httpMethod = handler.extractMapping(method).getHttpMethod();

      assertEquals(httpMethod, HttpMethod.DELETE);
    }

    @Test
    public void patchMapping_shouldExtract_HttpMethodPatch() throws NoSuchMethodException {
      Method method = ChildRestApi_Annotations.class.getMethod("patchMapping");

      RestApiProxyInvocationHandler handler = new RestApiProxyInvocationHandler(TestRestApi.class);
      HttpMethod httpMethod = handler.extractMapping(method).getHttpMethod();

      assertEquals(httpMethod, HttpMethod.PATCH);
    }
  }

  public static class ClassLevelMappingUrl {

    @Test
    public void shouldExtractSuperRootUrl() throws NoSuchMethodException {

      RestApiProxyInvocationHandler handler = new RestApiProxyInvocationHandler(TestRestApi.class);
      String url = handler.extractMapping(ChildRestApi_Annotations.class).getUrl();

      assertEquals(Config.SUPERRESTAPI_ROOTURL, url);
    }

    @Test
    public void shouldExtractCurrentRootUrl() throws NoSuchMethodException {

      RestApiProxyInvocationHandler handler = new RestApiProxyInvocationHandler(TestRestApi.class);
      String url = handler.extractMapping(SuperRestApi_Annotations.class).getUrl();

      assertEquals(Config.SUPERRESTAPI_ROOTURL, url);
    }
  }

  public static class MethodLevelMappingUrl {

    @Test
    public void shouldExtractMethodUrl() throws NoSuchMethodException {

      Method method = ChildRestApi_Annotations.class.getMethod("testMethod");
      RestApiProxyInvocationHandler handler = new RestApiProxyInvocationHandler(TestRestApi.class);
      String url = handler.extractMapping(method).getUrl();

      assertEquals(Config.TESTMETHOD_URL, url);
    }

    @Test
    public void shouldExtractMethodUrl_whenDeleteMapping() throws NoSuchMethodException {

      Method method = ChildRestApi_Annotations.class.getMethod("deleteMapping");
      RestApiProxyInvocationHandler handler = new RestApiProxyInvocationHandler(TestRestApi.class);
      String url = handler.extractMapping(method).getUrl();

      assertEquals("/{id}", url);
    }

  }

  public static class ExtractReturnType {

    @Test
    public void shouldExtractActualType_whenMethodIsFromSuperAndReturnyTypeIsGeneric() throws NoSuchMethodException {

      Method method = TestRestApi.class.getMethod("getOneGenericType", Integer.class);
      RestApiProxyInvocationHandler handler = new RestApiProxyInvocationHandler(TestRestApi.class);
      Type genericReturnType = method.getGenericReturnType();

      Class<?> extractedType = handler.extractReturnType(genericReturnType,
          false, TestRestApi.class);

      Class<?> expectedType = TestDto.class;

      assertEquals(expectedType, extractedType);
    }

    @Test
    public void shouldExtractActualType_whenMethodIsFromSuperAndReturnTypeIsPrimitive() throws NoSuchMethodException{

      Method method = TestRestApi.class.getMethod("update", TestDto.class);
      RestApiProxyInvocationHandler handler = new RestApiProxyInvocationHandler(TestRestApi.class);
      Type genericReturnType = method.getGenericReturnType();

      Class<?> extractedType = handler.extractReturnType(genericReturnType,
          false, TestRestApi.class);


      Class<?> expectedType = Integer.class;

      assertEquals(expectedType, extractedType);
    }


    @Test
    public void shouldExtractRestResponsePageImplType_whenMethodReturnsPage() throws NoSuchMethodException{

      Method method = TestRestApi.class.getMethod("filterAll", FilterDto.class);
      RestApiProxyInvocationHandler handler = new RestApiProxyInvocationHandler(TestRestApi.class);
      Type genericReturnType = method.getGenericReturnType();

      Class<?> extractedType = handler.extractReturnType(genericReturnType,
          true, TestRestApi.class);


      Class<?> expectedType = RestResponsePageImpl.class;

      assertEquals(expectedType, extractedType);
    }
  }
}