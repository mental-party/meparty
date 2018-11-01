package com.teammental.merest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.teammental.merest.testapp.TestApplication;
import com.teammental.merest.testapp.TestDto;
import com.teammental.merest.testrestapi.TestApplicationEnableRestApi;
import com.teammental.merest.testrestapi.TestRestApi;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = {TestApplicationEnableRestApi.class})
public class RestApiProxyBeanFactoryTest {

  private static SpringApplicationBuilder apiApplication;

  @Autowired
  private TestRestApi testRestApi;

  @BeforeClass
  public static void setUp() {

    int apiPort = 8090;
    apiApplication = new SpringApplicationBuilder(TestApplication.class)
        .properties("server.port=" + apiPort);

    apiApplication.run();

  }

  @Test
  public void testRestApi_shouldNotBeNull() {
    assertNotNull(testRestApi);
  }

  @Test
  public void getAll_shouldReturnStatusOk() {

    RestResponse<List<TestDto>> responseEntity = testRestApi.getAll();

    assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
  }

  @Test
  public void getAll_shouldReturnAsList() {

    RestResponse<List<TestDto>> responseEntity = testRestApi.getAll();

    List<TestDto> list = responseEntity.getBody();

    assertEquals(2, list.size());
    assertEquals((Integer) 1, list.get(0).getId());
    assertEquals("1", list.get(0).getName());
  }

  @Test
  public void getById_returnsDto_andStatusOk() {

    Integer expectedId = 1;
    String expectedName = "name";
    ResponseEntity<TestDto> responseEntity = testRestApi.getById(1);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(expectedId, responseEntity.getBody().getId());
    assertEquals(expectedName, responseEntity.getBody().getName());
  }

  @Test
  public void getById_shouldReturn404_whenNotFound() {

    RestResponse<TestDto> responseEntity = testRestApi.getById(5);

    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
  }

  @Test
  public void delete_shouldReturn204_whenSuccess() {
    RestResponse restResponse = testRestApi.delete(1);

    assertEquals(HttpStatus.NO_CONTENT, restResponse.getStatusCode());
  }

  @Test
  public void post_shouldExtractValidationDto_whenApiResponseWithValidationDto() {
    TestDto testDto = new TestDto();

    RestResponse restResponse = testRestApi.create(testDto);

    assertEquals(HttpStatus.BAD_REQUEST, restResponse.getStatusCode());
    assertNotNull(restResponse.getValidationResult());
  }

  @Test
  public void post_shouldReturn201_whenSuccess() {
    TestDto testDto = new TestDto(null, "name");

    RestResponse restResponse = testRestApi.create(testDto);

    assertEquals(HttpStatus.CREATED, restResponse.getStatusCode());
    assertTrue(restResponse.getHeaders().containsKey("Location"));
  }

  @AfterClass
  public static void cleanUp() {

    apiApplication.context().close();
  }
}