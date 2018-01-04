package com.teammental.merest.testapp;

import com.teammental.mecore.stereotype.controller.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(Config.SUPERRESTAPI_ROOTURL)
public interface SuperRestApi_Annotations extends Controller {


  @RequestMapping("supertestmethod")
  ResponseEntity testMethod();
}
