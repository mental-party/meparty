package com.teammental.merest.testrestapi;

import com.teammental.mecore.stereotype.controller.Controller;
import com.teammental.mecore.stereotype.controller.RestApiProxy;
import com.teammental.merest.testapp.Config;
import org.springframework.web.bind.annotation.RequestMapping;

@RestApiProxy(Config.TESTAPPLICATIONNAME)
@RequestMapping(Config.TESTRESTAPI_ROOTURL)
public interface MockRestApi extends Controller {
}
