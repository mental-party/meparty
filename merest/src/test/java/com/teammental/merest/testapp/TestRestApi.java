package com.teammental.merest.testapp;

import com.teammental.mecore.stereotype.controller.Controller;
import com.teammental.mecore.stereotype.controller.RestApi;
import com.teammental.merest.RestResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestApi(Config.TESTAPPLICATIONNAME)
@RequestMapping(Config.TESTRESTAPI_ROOTURL)
public interface TestRestApi extends BaseTitleRestApi<TestDto, Integer>, Controller {


  @GetMapping("/getunknownproperty")
  RestResponse<UnknownPropertyTo> getUnknownPropertyTo();
}
