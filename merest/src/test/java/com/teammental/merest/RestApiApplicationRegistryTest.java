package com.teammental.merest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.teammental.merest.autoconfiguration.RestApiApplication;
import com.teammental.merest.autoconfiguration.RestApiApplicationRegistry;
import com.teammental.merest.testrestapi.TestApplicationEnableRestApi;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = {TestApplicationEnableRestApi.class})
public class RestApiApplicationRegistryTest {

  @Autowired
  private RestApiApplicationRegistry restApiApplicationRegistry;

  @Test
  public void restApiApplicationRegistry_shouldNotBeNull() {
    assertNotNull(restApiApplicationRegistry);
  }

  @Test
  public void restApiApplicationRegistry_shouldContainConfiguredInResources() {

    final int restApiApplicationSize = restApiApplicationRegistry
        .getRestApiApplications().size();

    final int expected = 3;

    assertEquals(expected, restApiApplicationSize);
  }

  @Test
  public void shouldContainApplicationsInResources() {

    final boolean expectedUseMockImpl1 = true;
    final String expectedUrl1 = "http://localhost:8090";
    final String name1 = "testrestapi1";

    Optional<RestApiApplication> restApiApplicationOptional1
        = restApiApplicationRegistry
        .getRestApiApplication(name1);


    assertTrue(restApiApplicationOptional1.isPresent());
    assertEquals(expectedUseMockImpl1, restApiApplicationOptional1
        .get().getUseMockImpl());
    assertEquals(expectedUrl1, restApiApplicationOptional1
        .get().getUrl());

    final boolean expectedUseMockImpl2 = false;
    final String expectedUrl2 = "http://testrestapi2.com";
    final String name2 = "testrestapi2";

    Optional<RestApiApplication> restApiApplicationOptional2
        = restApiApplicationRegistry
        .getRestApiApplication(name2);

    assertTrue(restApiApplicationOptional2.isPresent());
    assertEquals(expectedUseMockImpl2, restApiApplicationOptional2
        .get().getUseMockImpl());
    assertEquals(expectedUrl2, restApiApplicationOptional2
        .get().getUrl());
  }

}