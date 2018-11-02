package com.teammental.merest.testrestapi;

import com.teammental.mecore.stereotype.controller.Controller;
import com.teammental.mecore.stereotype.controller.RestApiProxy;
import com.teammental.merest.RestResponse;
import com.teammental.merest.testapp.Config;
import com.teammental.merest.testapp.TestDto;
import com.teammental.merest.testapp.UnknownPropertyTo;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestApiProxy(Config.TESTAPPLICATIONNAME)
@RequestMapping(Config.TESTRESTAPI_ROOTURL)
public interface TestRestApi extends BaseTitleRestApi<TestDto, Integer>, Controller {


  @GetMapping("/getunknownproperty")
  RestResponse<UnknownPropertyTo> getUnknownPropertyTo();

  @GetMapping("/returnRequestParamValues")
  RestResponse<List<String>> returnRequestParamValues(
      @RequestParam("param1") String param1,
      @RequestParam("param2") String param2,
      @RequestParam("param3") String param3
  );
}
