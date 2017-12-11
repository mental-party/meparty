package com.teammental.meservice.testapp;

import com.teammental.merepository.BaseJpaRepository;
import com.teammental.meservice.BaseCrudServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestCrudServiceImpl extends BaseCrudServiceImpl<TestDto, Integer>
    implements TestCrudService {

  @Autowired
  private TestRepository testRepository;

  @Override
  protected BaseJpaRepository getRepository() {

    return testRepository;
  }

  @Override
  protected Class<TestDto> getDtoClass() {

    return TestDto.class;
  }

  @Override
  protected Class<TestEntity> getEntityClass() {

    return TestEntity.class;
  }
}
