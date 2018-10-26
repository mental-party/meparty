package com.teammental.merest.testrestapi;

import com.teammental.mecore.stereotype.controller.Controller;
import com.teammental.merest.testapp.Config;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(Config.SUPERRESTAPI_ROOTURL)
public interface SuperRestApi_Annotations extends Controller {


  @RequestMapping("supertestmethod")
  ResponseEntity testMethod();
}
