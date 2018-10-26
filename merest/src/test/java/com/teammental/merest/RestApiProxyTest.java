package com.teammental.merest;

import static org.junit.Assert.assertEquals;

import com.teammental.medto.FilterDto;
import com.teammental.merest.exception.NoRequestMappingFoundException;
import com.teammental.merest.testapp.ChildRestApi_Annotations;
import com.teammental.merest.testapp.Config;
import com.teammental.merest.testapp.TestDto;
import com.teammental.merest.testrestapi.SuperRestApi_Annotations;
import com.teammental.merest.testrestapi.TestRestApi;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class RestApiProxyTest {

  public static class HttpMethods {

    private RestTemplate restTemplate;

    @Before
    public void setUp() {
      restTemplate = new RestTemplate();
    }

    @Test
    public void requestMapping_shouldExtractHttpMethod() throws NoSuchMethodException {
      Method method = ChildRestApi_Annotations.class.getMethod("requestMapping_post");

      RestApiProxy handler = new RestApiProxy(restTemplate);
      HttpMethod httpMethod = handler.extractMapping(method).getHttpMethod();

      assertEquals(httpMethod, HttpMethod.POST);
    }

    @Test
    public void requestMapping_shouldExtract_HttpMethodGet_whenNoRequestMethodIsSet() throws NoSuchMethodException {
      Method method = ChildRestApi_Annotations.class.getMethod("requestMapping_noRequestMethodIsSet");

      RestApiProxy handler = new RestApiProxy(restTemplate);
      HttpMethod httpMethod = handler.extractMapping(method).getHttpMethod();

      assertEquals(httpMethod, HttpMethod.GET);
    }

    @Test(expected = NoRequestMappingFoundException.class)
    public void shouldThrowException_whenNoRequestMappingFound() throws NoSuchMethodException {
      Method method = ChildRestApi_Annotations.class.getMethod("noRequestMappingAnnotation");

      RestApiProxy handler = new RestApiProxy(restTemplate);
      HttpMethod httpMethod = handler.extractMapping(method).getHttpMethod();
    }

    @Test
    public void getMapping_shouldExtract_HttpMethodGet() throws NoSuchMethodException {
      Method method = ChildRestApi_Annotations.class.getMethod("getMapping");

      RestApiProxy handler = new RestApiProxy(restTemplate);
      HttpMethod httpMethod = handler.extractMapping(method).getHttpMethod();

      assertEquals(httpMethod, HttpMethod.GET);
    }

    @Test
    public void postMapping_shouldExtract_HttpMethodPost() throws NoSuchMethodException {
      Method method = ChildRestApi_Annotations.class.getMethod("postMapping");

      RestApiProxy handler = new RestApiProxy(restTemplate);
      HttpMethod httpMethod = handler.extractMapping(method).getHttpMethod();

      assertEquals(httpMethod, HttpMethod.POST);
    }

    @Test
    public void putMapping_shouldExtract_HttpMethodPut() throws NoSuchMethodException {
      Method method = ChildRestApi_Annotations.class.getMethod("putMapping");

      RestApiProxy handler = new RestApiProxy(restTemplate);
      HttpMethod httpMethod = handler.extractMapping(method).getHttpMethod();

      assertEquals(httpMethod, HttpMethod.PUT);
    }

    @Test
    public void deleteMapping_shouldExtract_HttpMethodDelete() throws NoSuchMethodException {
      Method method = ChildRestApi_Annotations.class.getMethod("deleteMapping");

      RestApiProxy handler = new RestApiProxy(restTemplate);
      HttpMethod httpMethod = handler.extractMapping(method).getHttpMethod();

      assertEquals(httpMethod, HttpMethod.DELETE);
    }

    @Test
    public void patchMapping_shouldExtract_HttpMethodPatch() throws NoSuchMethodException {
      Method method = ChildRestApi_Annotations.class.getMethod("patchMapping");

      RestApiProxy handler = new RestApiProxy(restTemplate);
      HttpMethod httpMethod = handler.extractMapping(method).getHttpMethod();

      assertEquals(httpMethod, HttpMethod.PATCH);
    }
  }

  public static class ClassLevelMappingUrl {

    private RestTemplate restTemplate;

    @Before
    public void setUp() {
      restTemplate = new RestTemplate();
    }

    @Test
    public void shouldExtractSuperRootUrl() throws NoSuchMethodException {

      RestApiProxy handler = new RestApiProxy(restTemplate);
      String url = handler.extractMapping(ChildRestApi_Annotations.class).getUrl();

      assertEquals(Config.SUPERRESTAPI_ROOTURL, url);
    }

    @Test
    public void shouldExtractCurrentRootUrl() throws NoSuchMethodException {

      RestApiProxy handler = new RestApiProxy(restTemplate);
      String url = handler.extractMapping(SuperRestApi_Annotations.class).getUrl();

      assertEquals(Config.SUPERRESTAPI_ROOTURL, url);
    }
  }

  public static class MethodLevelMappingUrl {

    private RestTemplate restTemplate;

    @Before
    public void setUp() {
      restTemplate = new RestTemplate();
    }

    @Test
    public void shouldExtractMethodUrl() throws NoSuchMethodException {

      Method method = ChildRestApi_Annotations.class.getMethod("testMethod");
      RestApiProxy handler = new RestApiProxy(restTemplate);
      String url = handler.extractMapping(method).getUrl();

      assertEquals(Config.TESTMETHOD_URL, url);
    }

    @Test
    public void shouldExtractMethodUrl_whenDeleteMapping() throws NoSuchMethodException {

      Method method = ChildRestApi_Annotations.class.getMethod("deleteMapping");
      RestApiProxy handler = new RestApiProxy(restTemplate);
      String url = handler.extractMapping(method).getUrl();

      assertEquals("/{id}", url);
    }

  }

  public static class ExtractReturnType {

    private RestTemplate restTemplate;

    @Before
    public void setUp() {
      restTemplate = new RestTemplate();
    }

    @Test
    public void shouldExtractActualType_whenMethodIsFromSuperAndReturnyTypeIsGeneric() throws NoSuchMethodException {

      Method method = TestRestApi.class.getMethod("getOneGenericType", Integer.class);
      RestApiProxy handler = new RestApiProxy(restTemplate);
      Type genericReturnType = method.getGenericReturnType();

      Class<?> extractedType = handler.extractReturnType(genericReturnType,
          false, TestRestApi.class);

      Class<?> expectedType = TestDto.class;

      assertEquals(expectedType, extractedType);
    }

    @Test
    public void shouldExtractActualType_whenMethodIsFromSuperAndReturnTypeIsPrimitive() throws NoSuchMethodException {

      Method method = TestRestApi.class.getMethod("update", TestDto.class);
      RestApiProxy handler = new RestApiProxy(restTemplate);
      Type genericReturnType = method.getGenericReturnType();

      Class<?> extractedType = handler.extractReturnType(genericReturnType,
          false, TestRestApi.class);


      Class<?> expectedType = Integer.class;

      assertEquals(expectedType, extractedType);
    }


    @Test
    public void shouldExtractRestResponsePageImplType_whenMethodReturnsPage() throws NoSuchMethodException {

      Method method = TestRestApi.class.getMethod("filterAll", FilterDto.class);
      RestApiProxy handler = new RestApiProxy(restTemplate);
      Type genericReturnType = method.getGenericReturnType();

      Class<?> extractedType = handler.extractReturnType(genericReturnType,
          true, TestRestApi.class);


      Class<?> expectedType = RestResponsePageImpl.class;

      assertEquals(expectedType, extractedType);
    }
  }
}