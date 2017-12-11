package com.teammental.meservice;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.teammental.meexception.dto.DtoCrudException;
import com.teammental.memapper.MeMapper;
import com.teammental.meservice.testapp.TestCrudService;
import com.teammental.meservice.testapp.TestCrudServiceImpl;
import com.teammental.meservice.testapp.TestDto;
import com.teammental.meservice.testapp.TestEntity;
import com.teammental.meservice.testapp.TestRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class BaseCrudServiceImplTest {

  @MockBean
  private TestRepository testRepository;

  @InjectMocks
  private TestCrudService testCrudService = new TestCrudServiceImpl();

  @Before
  public void setUp() {

    MockitoAnnotations.initMocks(this);
  }

  // region findAll

  @Test
  public void findAll_shouldReturnAll_whenSuccess() throws DtoCrudException {

    final TestEntity testEntity1 = TestEntity.buildRandom();
    final TestEntity testEntity2 = TestEntity.buildRandom();
    final List<TestEntity> testEntities = Arrays.asList(testEntity1, testEntity2);
    final List<TestDto> expectedDtos = (List<TestDto>) MeMapper.getMapperFromList(testEntities)
        .mapToList(TestDto.class).get();
    final String expectedDtoString = expectedDtos.stream()
        .map(testDto -> testDto.toString())
        .reduce("", String::concat);


    when(testRepository.findAll())
        .thenReturn(testEntities);

    final List<TestDto> actualTestDtos = testCrudService.findAll();
    final String actualDtoString = actualTestDtos.stream()
        .map(testDto -> testDto.toString())
        .reduce("", String::concat);

    assertEquals(expectedDtoString, actualDtoString);

    verify(testRepository, times(1))
        .findAll();

    verifyNoMoreInteractions(testRepository);
  }

  @Test
  public void findAll_shouldThrowNotFoundException_whenNotFound() {

    when(testRepository.findAll())
        .thenReturn(null);

    try {
      testCrudService.findAll();
      fail();
    } catch (DtoCrudException ex) {
      verify(testRepository, times(1))
          .findAll();
      verifyNoMoreInteractions(testRepository);
    }
  }

  // endregion findAll

  // region findOne

  @Test
  public void findOne_shouldReturnItem_whenFound() throws DtoCrudException {

    final TestEntity testEntity = TestEntity.buildRandom();
    final Integer id = testEntity.getId();
    final TestDto expectedDto = (TestDto) MeMapper.getMapperFrom(testEntity)
        .mapTo(TestDto.class).get();

    when(testRepository.findById(id))
        .thenReturn(Optional.of(testEntity));

    final TestDto actualDto = testCrudService.findById(id);

    assertEquals(expectedDto.toString(), actualDto.toString());

    verify(testRepository, times(1))
        .findById(id);
    verifyNoMoreInteractions(testRepository);
  }

  @Test
  public void findOne_shouldThrowNotFoundException_whenNotFound() {

    final Integer id = 58439;
    when(testRepository.findById(id))
        .thenReturn(Optional.ofNullable(null));

    try {
      testCrudService.findById(id);
      fail();
    } catch (DtoCrudException ex) {
      verify(testRepository, times(1))
          .findById(id);
      verifyNoMoreInteractions(testRepository);
    }
  }

  // endregion findOne

  // region insert

  @Test
  public void create_shouldThrowcreateException_whenFails() {

    final TestDto testDto = TestDto.buildRandom();

    when(testRepository.save(anyObject()))
        .thenThrow(new RuntimeException());

    try {
      testCrudService.create(testDto);
      fail();
    } catch (DtoCrudException ex) {
      verify(testRepository, times(1))
          .save(anyObject());
      verifyNoMoreInteractions(testRepository);
    }
  }

  @Test
  public void create_shouldReturnId_whenSuccess() throws DtoCrudException {

    final TestEntity expectedEntity = TestEntity.buildRandom();
    final Integer expectedId = expectedEntity.getId();
    final TestDto expectedDto = (TestDto) MeMapper.getMapperFrom(expectedEntity)
        .mapTo(TestDto.class).get();

    when(testRepository.save(anyObject()))
        .thenReturn(expectedEntity);

    final Integer actualId = testCrudService.create(expectedDto);

    assertEquals(expectedId, actualId);

    verify(testRepository, times(1))
        .save(anyObject());
  }

  // endregion create

  // region update

  @Test
  public void update_shouldUpdateAndReturnUpdatedItem()
      throws DtoCrudException {

    final TestEntity originalEntity = TestEntity.buildRandom();

    final TestEntity updatedEntity = TestEntity.buildRandom();
    updatedEntity.setId(originalEntity.getId());

    final TestDto updatedDto = (TestDto) MeMapper.getMapperFrom(updatedEntity)
        .mapTo(TestDto.class).get();

    when(testRepository.findById(anyInt()))
        .thenReturn(Optional.of(originalEntity));

    when(testRepository.save(anyObject()))
        .thenReturn(updatedEntity);

    final Integer actualId = testCrudService.update(updatedDto);

    assertEquals(originalEntity.getId(), actualId);

    verify(testRepository, times(1))
        .findById(anyInt());

    verify(testRepository, times(1))
        .save(anyObject());

    verifyNoMoreInteractions(testRepository);
  }


  @Test
  public void update_shouldThrowUpdateException_whenFails() throws DtoCrudException {

    final TestEntity originalEntity = TestEntity.buildRandom();

    final TestDto updatedDto = TestDto.buildRandom();

    when(testRepository.findById(anyInt()))
        .thenReturn(Optional.of(originalEntity));

    when(testRepository.save(anyObject()))
        .thenThrow(new RuntimeException());

    try {
      testCrudService.update(updatedDto);
      fail();
    } catch (DtoCrudException ex) {
      verify(testRepository, times(1))
          .findById(anyInt());
      verify(testRepository, times(1))
          .save(anyObject());
      verifyNoMoreInteractions(testRepository);
    }
  }

  @Test
  public void update_shouldThrowNotFoundException_whenNotFound() throws DtoCrudException {

    final TestEntity originalEntity = TestEntity.buildRandom();
    final TestDto updatedDto = TestDto.buildRandom();

    when(testRepository.findById(anyInt()))
        .thenReturn(Optional.empty());

    when(testRepository.save(anyObject()))
        .thenReturn(originalEntity);

    try {
      testCrudService.update(updatedDto);
      fail();
    } catch (DtoCrudException e) {
      verify(testRepository, times(1))
          .findById(anyInt());

      verify(testRepository, times(0))
          .save(anyObject());

      verifyNoMoreInteractions(testRepository);
    }
  }

  // endregion update

  // region delete

  @Test
  public void delete_shouldThrowNotFoundException_whenNotFound() throws DtoCrudException {

    final Integer id = 84329;
    Mockito.doThrow(new RuntimeException())
        .when(testRepository)
        .deleteById(id);

    try {
      testCrudService.delete(id);
      fail();
    } catch (DtoCrudException ex) {
      verify(testRepository, times(1))
          .deleteById(id);

      verifyNoMoreInteractions(testRepository);
    }
  }

  @Test
  public void delete_shouldDeleteItem_whenFoundItem()
      throws DtoCrudException {

    final Integer id = 8932;

    boolean result = testCrudService.delete(id);

    assertTrue(result);

    verify(testRepository, times(1))
        .deleteById(id);
  }


  // endregion delete
}