package com.teammental.merest;

import static org.junit.Assert.assertEquals;

import com.teammental.medto.impl.FilterDtoImpl;
import com.teammental.medto.page.PageRequestDto;
import com.teammental.merest.testapp.TestApplication;
import com.teammental.merest.testapp.TestDto;
import com.teammental.merest.testrestapi.TestApplicationEnableRestApi;
import com.teammental.merest.testrestapi.TestRestApi;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = {TestApplicationEnableRestApi.class})
public class FilterDtoHandlerInterceptorTest {

  private SpringApplicationBuilder apiApplication;

  @Autowired
  private TestRestApi testRestApi;

  @Before
  public void setUp() {

    int apiPort = 8090;
    apiApplication = new SpringApplicationBuilder(TestApplication.class)
        .properties("server.port=" + apiPort);

    apiApplication.run();
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

    apiApplication.context().close();
  }
}
