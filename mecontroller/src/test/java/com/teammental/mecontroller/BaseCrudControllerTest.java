package com.teammental.mecontroller;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import com.teammental.mecontroller.config.TestControllerConfig;
import com.teammental.mecontroller.config.TestUtil;
import com.teammental.mecontroller.testapp.TestCrudRestApi;
import com.teammental.mecontroller.testapp.TestCrudController;
import com.teammental.mecontroller.testapp.TestCrudService;
import com.teammental.mecontroller.testapp.TestDto;
import com.teammental.mecontroller.testapp.TestHandler;
import com.teammental.meexception.dto.DtoCreateException;
import com.teammental.meexception.dto.DtoCrudException;
import com.teammental.meexception.dto.DtoNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SuppressWarnings("PMD.TooManyStaticImports")
@RunWith(SpringJUnit4ClassRunner.class)
@Import(TestControllerConfig.class)
public class BaseCrudControllerTest {

  @MockBean
  private TestCrudService testCrudService;

  @InjectMocks
  private TestCrudRestApi testCrudRestApi = new TestCrudController();

  private MockMvc mockMvc;

  @Before
  public void setUp() {

    MockitoAnnotations.initMocks(this);

    this.mockMvc = MockMvcBuilders.standaloneSetup(testCrudRestApi)
        .setControllerAdvice(new TestHandler())
        .build();

  }

  // region getAll

  @Test
  public void getAll_shouldReturn200AndDtos_whenFound() throws Exception {
    final TestDto testDto1 = TestDto.buildRandom();
    final TestDto testDto2 = TestDto.buildRandom();
    final List<TestDto> expectedDtos = Arrays.asList(testDto1, testDto2);

    when(testCrudService.findAll())
        .thenReturn(expectedDtos);

    mockMvc.perform(get(TestControllerConfig.URL))
        .andExpect(status().isOk())
        .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id", is(testDto1.getId())))
        .andExpect(jsonPath("$[0].name", is(testDto1.getName())))
        .andExpect(jsonPath("$[1].id", is(testDto2.getId())))
        .andExpect(jsonPath("$[1].name", is(testDto2.getName())));

    verify(testCrudService, times(1))
        .findAll();

    verifyNoMoreInteractions(testCrudService);

  }


  @Test
  public void getAll_shouldThrowException_whenNotFound() throws Exception {

    when(testCrudService.findAll())
        .thenThrow(new DtoNotFoundException());

    mockMvc.perform(get(TestControllerConfig.URL))
        .andExpect(status().isInternalServerError());
    verify(testCrudService, times(1))
        .findAll();
    verifyNoMoreInteractions(testCrudService);

  }

  // endregion getAll

  // region create

  @Test
  public void create_shouldReturn201AndLocation_whenCreate() throws Exception {

    final Integer expectedId = 9843;
    when(testCrudService.create(anyObject()))
        .thenReturn(expectedId);

    final TestDto testDto = TestDto.buildRandom();

    mockMvc.perform(post(TestControllerConfig.URL)
        .content(TestUtil.convertObjectToJsonBytes(testDto))
        .contentType(TestUtil.APPLICATION_JSON_UTF8))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location",
            TestControllerConfig.URL + "/" + expectedId));

    verify(testCrudService, times(1))
        .create(anyObject());

    verifyNoMoreInteractions(testCrudService);
  }

  @Test
  public void create_shouldThrowException_whenValidationFails() throws Exception {
    final TestDto testDto = new TestDto();
    mockMvc.perform(post(TestControllerConfig.URL)
        .contentType(TestUtil.APPLICATION_JSON_UTF8)
        .content(TestUtil.convertObjectToJsonBytes(testDto)))
        .andExpect(status().isBadRequest());

    verify(testCrudService, times(0))
        .create(anyObject());
  }

  @Test
  public void create_shouldThrowException_whenCreateException() throws Exception {

    TestDto testDto = TestDto.buildRandom();
    when(testCrudService.create(anyObject()))
        .thenThrow(new DtoCreateException(null));

    mockMvc.perform(post(TestControllerConfig.URL)
        .content(TestUtil.convertObjectToJsonBytes(testDto))
        .contentType(TestUtil.APPLICATION_JSON_UTF8))
        .andExpect(status().isInternalServerError());

    verify(testCrudService, times(1))
        .create(anyObject());

    verifyNoMoreInteractions(testCrudService);
  }

  // endregion create

  // region update

  @Test
  public void update_shouldReturn200_whenSuccess() throws Exception {
    final TestDto testDto = TestDto.buildRandom();

    when(testCrudService.update(anyObject()))
        .thenReturn(testDto.getId());

    mockMvc.perform(put(TestControllerConfig.URL)
        .contentType(TestUtil.APPLICATION_JSON_UTF8)
        .content(TestUtil.convertObjectToJsonBytes(testDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", is(testDto.getId())));

    verify(testCrudService, times(1))
        .update(anyObject());

    verifyNoMoreInteractions(testCrudService);
  }

  @Test
  public void update_shouldThrowException_whenNotFound() throws Exception {
    final TestDto testDto = TestDto.buildRandom();

    when(testCrudService.update(anyObject()))
        .thenThrow(new DtoCrudException(null));

    mockMvc.perform(put(TestControllerConfig.URL)
        .content(TestUtil.convertObjectToJsonBytes(testDto))
        .contentType(TestUtil.APPLICATION_JSON_UTF8))
        .andExpect(status().isInternalServerError());

    verify(testCrudService, times(1))
        .update(anyObject());

    verifyNoMoreInteractions(testCrudService);
  }

  @Test
  public void update_shouldThrowException_whenValidationFails() throws Exception {
    final TestDto testDto = new TestDto();

    mockMvc.perform(put(TestControllerConfig.URL)
        .contentType(TestUtil.APPLICATION_JSON_UTF8)
        .content(TestUtil.convertObjectToJsonBytes(testDto)))
        .andExpect(status().isBadRequest());

    verifyZeroInteractions(testCrudService);
  }


  // endregion update
}