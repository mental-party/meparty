package com.teammental.merest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import com.teammental.mevalidation.dto.ValidationResultDto;

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
    ResponseEntity<TestDto> responseEntity =  testRestApi.getById(1);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(expectedId, responseEntity.getBody().getId());
    assertEquals(expectedName, responseEntity.getBody().getName());
  }

  @Test
  public void getById_shouldReturn404_whenNotFound() {

    RestResponse<TestDto> responseEntity = (RestResponse<TestDto>) testRestApi.getById(5);

    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
  }

  @Test
  public void delete_shouldReturn204_whenSuccess() {
    RestResponse restResponse = (RestResponse) testRestApi.delete(1);

    assertEquals(HttpStatus.NO_CONTENT, restResponse.getStatusCode());
  }

  @Test
  public void post_shouldExtractValidationDto_whenApiResponseWithValidationDto() {
    TestDto testDto = new TestDto();

    RestResponse restResponse = (RestResponse) testRestApi.create(testDto);

    assertEquals(HttpStatus.BAD_REQUEST, restResponse.getStatusCode());
    assertNotNull(restResponse.getValidationResult());
  }

  @Test
  public void post_shouldReturn201_whenSuccess() {
    TestDto testDto = new TestDto(null, "name");

    RestResponse restResponse = (RestResponse) testRestApi.create(testDto);

    assertEquals(HttpStatus.CREATED, restResponse.getStatusCode());
    assertTrue(restResponse.getHeaders().containsKey("Location"));
  }

  @After
  public void cleanUp() {

    ApplicationExplorer.clean();
  }
}