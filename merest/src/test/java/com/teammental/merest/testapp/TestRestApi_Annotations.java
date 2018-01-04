package com.teammental.merest.testapp;

import com.teammental.mecore.stereotype.controller.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface TestRestApi_Annotations extends Controller {

  @RequestMapping(value = "/", method = RequestMethod.POST)
  ResponseEntity requestMapping_post();

  @RequestMapping
  ResponseEntity requestMapping_noRequestMethodIsSet();

  @GetMapping
  ResponseEntity getMapping();

  @PostMapping
  ResponseEntity postMapping();

  @PutMapping
  ResponseEntity putMapping();

  @DeleteMapping
  ResponseEntity deleteMapping();

  @PatchMapping
  ResponseEntity patchMapping();

  ResponseEntity noRequestMappingAnnotation();

}
