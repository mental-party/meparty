package com.teammental.mehandler.testapp;

import com.teammental.meexception.RestfulException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TestController {

  @GetMapping("/")
  public String getIndex() throws RestfulException {
    return "test";
  }

  @PostMapping("/")
  public String postIndex(@Validated TestDto testDto) {
    return "test";
  }
}
