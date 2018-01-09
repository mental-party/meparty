package com.teammental.merest;

import static org.junit.Assert.assertEquals;

import com.teammental.merest.exception.ApplicationNameCannotBeNullOrEmptyException;
import com.teammental.merest.exception.RestApiAnnotationIsMissingException;
import com.teammental.merest.testapp.NoRestApiAnnotation;
import com.teammental.merest.testapp.RestApiWithNoApplicationName;

import java.util.List;

import com.teammental.merest.testapp.Config;
import com.teammental.merest.testapp.TestApplication;
import com.teammental.merest.testapp.TestDto;
import com.teammental.merest.testapp.TestRestApi;
import com.teammental.merest.testapp.TestRestApiController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = {TestApplication.class})
public class RestApiProxyFactoryTest {

  @LocalServerPort
  private int port;

  private TestRestApi testRestApi = RestApiProxyFactory.createProxy(TestRestApi.class);

  @Before
  public void setUp() {

    ApplicationExplorer.addApplicationUrl(Config.TESTAPPLICATIONNAME, "http://localhost:" + port);
  }

  @Test(expected = RestApiAnnotationIsMissingException.class)
  public void shouldThrowException_whenRestApiAnnotationIsMissing() {

    NoRestApiAnnotation noRestApiAnnotation = RestApiProxyFactory
        .createProxy(NoRestApiAnnotation.class);
  }

  @Test(expected = ApplicationNameCannotBeNullOrEmptyException.class)
  public void shouldThrowException_whenApplicationNameIsMissing() {

    RestApiWithNoApplicationName restApi = RestApiProxyFactory
        .createProxy(RestApiWithNoApplicationName.class);
  }

  @Test
  public void getAll_shouldReturnStatusOk() {

    RestResponse<List<TestDto>> responseEntity = (RestResponse<List<TestDto>>) testRestApi.getAll();

    assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
  }

  @Test
  public void getAll_shouldReturnAsList() {

    RestResponse<List<TestDto>> responseEntity = (RestResponse<List<TestDto>>) testRestApi.getAll();

    List<TestDto> list = responseEntity.getBody();

    assertEquals(2, list.size());
    assertEquals((Integer) 1, list.get(0).getId());
    assertEquals("1", list.get(0).getName());
  }

  @Test
  public void getById_returnsDto_andStatusOk() {

    Integer expectedId = 1;
    String expectedName = "name";
    RestResponse<TestDto> responseEntity = (RestResponse<TestDto>) testRestApi.getById(1);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(expectedId, responseEntity.getBody().getId());
    assertEquals(expectedName, responseEntity.getBody().getName());
  }

  @Test
  public void getById_shouldReturn404_whenNotFound() {

    RestResponse<TestDto> responseEntity = (RestResponse<TestDto>) testRestApi.getById(5);

    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
  }

  @After
  public void cleanUp() {

    ApplicationExplorer.clean();
  }
}