package com.teammental.merest;

import com.teammental.medto.impl.FilterDtoImpl;
import com.teammental.medto.page.PageRequestDto;
import com.teammental.merest.testapp.Config;
import com.teammental.merest.testapp.TestApplication;
import com.teammental.merest.testapp.TestDto;
import com.teammental.merest.testrestapi.TestRestApi;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = {TestApplication.class})
public class FilterDtoHandlerInterceptorTest {

  @LocalServerPort
  private int port;

  private TestRestApi testRestApi = RestApiProxyFactory.createProxy(TestRestApi.class);

  private ApplicationExplorer applicationExplorer = ApplicationExplorer.getInstance();

  @Before
  public void setUp() {

    applicationExplorer.addApplication(Config.TESTAPPLICATIONNAME, "http://localhost:" + port);
  }

  @Test
  public void filterAll_shouldReturnAsPage() {

    FilterDtoImpl filterDto = new FilterDtoImpl();
    filterDto.setPage(new PageRequestDto(1, 2));

    RestResponse<Page<TestDto>> responseEntity = testRestApi.filterAll(filterDto);

    Page<TestDto> page = responseEntity.getBody();

    assertEquals(2, page.getContent().size());
    assertEquals((Integer) 1, page.getContent().get(0).getId());
    assertEquals("1", page.getContent().get(0).getName());
  }


  @After
  public void cleanUp() {

    applicationExplorer.clean();
  }
}
