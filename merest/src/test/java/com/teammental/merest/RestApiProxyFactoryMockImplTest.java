package com.teammental.merest;

import static org.junit.Assert.assertTrue;

import com.teammental.merest.autoconfiguration.ApplicationExplorer;
import com.teammental.merest.testapp.Config;
import com.teammental.merest.testapp.MockRestApiMockImpl;
import com.teammental.merest.testrestapi.MockRestApi;
import com.teammental.merest.testrestapi.TestApplicationEnableRestApi;
import com.teammental.merest.testrestapi.TestRestApi;
import java.lang.reflect.Proxy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = {TestApplicationEnableRestApi.class},
    properties = {"com.teammental.merest.use-mock-impl=true"})
public class RestApiProxyFactoryMockImplTest {

  @LocalServerPort
  private int port;

  @Autowired
  private MockRestApi mockRestApi;

  @Autowired
  private TestRestApi testRestApi;

  @Autowired
  private ApplicationExplorer applicationExplorer;

  @Before
  public void setUp() {

    applicationExplorer.addApplication(Config.TESTAPPLICATIONNAME, "http://localhost:" + port);

  }

  @Test
  public void shouldReturnMockImplInstance_whenMockImplFound() {

    assertTrue(mockRestApi instanceof MockRestApiMockImpl);
  }


  @Test
  public void shouldReturnProxyInstance_whenMockImplNotFound() {

    assertTrue(testRestApi instanceof Proxy);
  }

  @After
  public void cleanUp() {

    applicationExplorer.removeApplication(Config.TESTAPPLICATIONNAME);
  }
}