package com.teammental.mecontroller.testapp;

import com.teammental.mecontroller.rest.BaseCrudController;
import com.teammental.mecontroller.config.TestControllerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestCrudController
    extends BaseCrudController<TestCrudService, TestDto, Integer>
    implements TestCrudRestApi {

  @Autowired
  private TestCrudService testCrudService;

  @Override
  protected TestCrudService getCrudService() {

    return testCrudService;
  }

  @Override
  protected String getMappingUrlOfController() {

    return TestControllerConfig.URL;
  }
}
