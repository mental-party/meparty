package com.teammental.merest;

import static org.junit.Assert.assertTrue;

import com.teammental.merest.testrestapi.MockRestApiMockImpl;
import com.teammental.merest.testrestapi.MockRestApi;
import com.teammental.merest.testrestapi.TestApplicationEnableRestApi;
import com.teammental.merest.testrestapi.TestRestApi;
import java.lang.reflect.Proxy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = {TestApplicationEnableRestApi.class},
properties = {"com.teammental.merest.rest-api-applications[0].use-mock-impl=true",
    "com.teammental.merest.rest-api-applications[0].name=testrestapi1",
    "com.teammental.merest.rest-api-applications[0].url=http://testrestapi1.com"})
public class RestApiProxyFactoryMockImplTest {

  @Autowired
  private MockRestApi mockRestApi;

  @Autowired
  private TestRestApi testRestApi;


  @Test
  public void shouldReturnMockImplInstance_whenMockImplFound() {

    assertTrue(mockRestApi instanceof MockRestApiMockImpl);
  }


  @Test
  public void shouldReturnProxyInstance_whenMockImplNotFound() {

    assertTrue(testRestApi instanceof Proxy);
  }

}