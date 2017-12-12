package com.teammental.mehandler;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.teammental.meexception.RestfulException;
import com.teammental.mehandler.testapp.TestController;
import com.teammental.mehandler.testapp.TestDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
public class RestfulExceptionHandlerTest {

  private TestController testController;

  private MockMvc mockMvc;

  @Before
  public void setUp() {

    testController = Mockito.mock(TestController.class);

    this.mockMvc = MockMvcBuilders.standaloneSetup(testController)
        .setControllerAdvice(new RestfulExceptionHandler())
        .build();
  }


  @Test
  public void shouldReturnStatusCode_whenStatusCodeIsResolved() throws Exception {

    final int status = HttpStatus.NOT_FOUND.value();

    final RestfulException restfulException = RestfulException.getBuilder()
        .statusCode(status).build();


    when(testController.getIndex())
        .thenThrow(restfulException);

    mockMvc.perform(get("/"))
        .andExpect(status().is(status));
  }

  @Test
  public void shouldReturnBadRequest_whenStatusCodeIsNotResolved() throws Exception {

    final int status = 123456;

    final RestfulException restfulException = RestfulException.getBuilder()
        .statusCode(status).build();


    when(testController.getIndex())
        .thenThrow(restfulException);

    mockMvc.perform(get("/"))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnDto_whenDtoIsSupplied() throws Exception {

    final TestDto dto = new TestDto(1, "name");

    final RestfulException restfulException = RestfulException.getBuilder()
        .dto(dto).build();

    when(testController.getIndex())
        .thenThrow(restfulException);

    mockMvc.perform(get("/"))
        .andExpect(jsonPath("$.id", is(dto.getId())))
        .andExpect(jsonPath("$.name", is(dto.getName())));
  }

  @Test
  public void shouldReturnExceptionMessage_whenDtoIsNotSupplied() throws Exception {

    final String message = "ExceptionMessage";

    final RestfulException restfulException = RestfulException.getBuilder()
        .message(message).build();

    when(testController.getIndex())
        .thenThrow(restfulException);

    mockMvc.perform(get("/"))
        .andExpect(jsonPath("$", is(message)));
  }
}