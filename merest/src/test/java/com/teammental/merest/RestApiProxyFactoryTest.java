package com.teammental.merest;

import static org.junit.Assert.assertEquals;

import java.util.List;

import com.teammental.merest.testapp.Config;
import com.teammental.merest.testapp.TestApplication;
import com.teammental.merest.testapp.TestDto;
import com.teammental.merest.testapp.TestRestApi;
import com.teammental.merest.testapp.TestRestApiController;
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

  @Test
  public void getAll_shouldReturn2Item_andStatusOk() {
    ResponseEntity<List<TestDto>> responseEntity = testRestApi.getAll();

    assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
  }
}