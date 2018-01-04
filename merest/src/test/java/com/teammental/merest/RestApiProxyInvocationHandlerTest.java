package com.teammental.merest;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import com.teammental.merest.exception.NoRequestMappingFoundException;
import com.teammental.merest.testapp.TestRestApi_Annotations;
import org.junit.Test;
import org.springframework.http.HttpMethod;

public class RestApiProxyInvocationHandlerTest {

  public static class MappingAnnotations {

    @Test
    public void requestMapping_shouldExtractHttpMethod() throws NoSuchMethodException {
      Method method = TestRestApi_Annotations.class.getMethod("requestMapping_post");

      RestApiProxyInvocationHandler handler = new RestApiProxyInvocationHandler();
      HttpMethod httpMethod = handler.extractHttpMethod(method);

      assertEquals(httpMethod, HttpMethod.POST);
    }

    @Test
    public void requestMapping_shouldExtract_HttpMethodGet_whenNoRequestMethodIsSet() throws NoSuchMethodException {
      Method method = TestRestApi_Annotations.class.getMethod("requestMapping_noRequestMethodIsSet");

      RestApiProxyInvocationHandler handler = new RestApiProxyInvocationHandler();
      HttpMethod httpMethod = handler.extractHttpMethod(method);

      assertEquals(httpMethod, HttpMethod.GET);
    }

    @Test(expected = NoRequestMappingFoundException.class)
    public void shouldThrowException_whenNoRequestMappingFound() throws NoSuchMethodException {
      Method method = TestRestApi_Annotations.class.getMethod("noRequestMappingAnnotation");

      RestApiProxyInvocationHandler handler = new RestApiProxyInvocationHandler();
      HttpMethod httpMethod = handler.extractHttpMethod(method);
    }

    @Test
    public void getMapping_shouldExtract_HttpMethodGet() throws NoSuchMethodException {
      Method method = TestRestApi_Annotations.class.getMethod("getMapping");

      RestApiProxyInvocationHandler handler = new RestApiProxyInvocationHandler();
      HttpMethod httpMethod = handler.extractHttpMethod(method);

      assertEquals(httpMethod, HttpMethod.GET);
    }

    @Test
    public void postMapping_shouldExtract_HttpMethodPost() throws NoSuchMethodException {
      Method method = TestRestApi_Annotations.class.getMethod("postMapping");

      RestApiProxyInvocationHandler handler = new RestApiProxyInvocationHandler();
      HttpMethod httpMethod = handler.extractHttpMethod(method);

      assertEquals(httpMethod, HttpMethod.POST);
    }

    @Test
    public void putMapping_shouldExtract_HttpMethodPut() throws NoSuchMethodException {
      Method method = TestRestApi_Annotations.class.getMethod("putMapping");

      RestApiProxyInvocationHandler handler = new RestApiProxyInvocationHandler();
      HttpMethod httpMethod = handler.extractHttpMethod(method);

      assertEquals(httpMethod, HttpMethod.PUT);
    }

    @Test
    public void deleteMapping_shouldExtract_HttpMethodDelete() throws NoSuchMethodException {
      Method method = TestRestApi_Annotations.class.getMethod("deleteMapping");

      RestApiProxyInvocationHandler handler = new RestApiProxyInvocationHandler();
      HttpMethod httpMethod = handler.extractHttpMethod(method);

      assertEquals(httpMethod, HttpMethod.DELETE);
    }

    @Test
    public void patchMapping_shouldExtract_HttpMethodPatch() throws NoSuchMethodException {
      Method method = TestRestApi_Annotations.class.getMethod("patchMapping");

      RestApiProxyInvocationHandler handler = new RestApiProxyInvocationHandler();
      HttpMethod httpMethod = handler.extractHttpMethod(method);

      assertEquals(httpMethod, HttpMethod.PATCH);
    }
  }
}