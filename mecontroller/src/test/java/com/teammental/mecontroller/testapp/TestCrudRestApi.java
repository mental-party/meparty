package com.teammental.mecontroller.testapp;

import com.teammental.mecontroller.config.TestControllerConfig;
import com.teammental.mecontroller.rest.BaseCrudRestApi;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(TestControllerConfig.URL)
public interface TestCrudRestApi extends BaseCrudRestApi<TestDto, Integer> {
}
