package com.teammental.mehandler;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.teammental.mehandler.testapp.TestController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
public class ValidationErrorHandlerTest {

  private TestController testController;

  private MockMvc mockMvc;

  @Before
  public void setUp() {

    testController = Mockito.mock(TestController.class);

    this.mockMvc = MockMvcBuilders.standaloneSetup(testController)
        .setControllerAdvice(new ValidationErrorHandler())
        .build();
  }

  @Test
  public void shouldReturnBadRequest_whenValidationFails() throws Exception {

    mockMvc.perform(post("/"))
        .andExpect(status().isBadRequest());
  }

}