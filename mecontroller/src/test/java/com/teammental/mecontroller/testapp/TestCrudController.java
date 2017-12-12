package com.teammental.mecontroller.testapp;

import com.teammental.mecontroller.BaseCrudController;
import com.teammental.mecontroller.config.TestControllerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(TestControllerConfig.URL)
public class TestCrudController extends BaseCrudController<TestCrudService, TestDto, Integer> {

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
