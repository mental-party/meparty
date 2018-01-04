package com.teammental.merest.testapp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface ChildRestApi_Annotations extends SuperRestApi_Annotations {

  @RequestMapping(value = "/", method = RequestMethod.POST)
  ResponseEntity requestMapping_post();

  @RequestMapping
  ResponseEntity requestMapping_noRequestMethodIsSet();

  @GetMapping("/get")
  ResponseEntity getMapping();

  @PostMapping
  ResponseEntity postMapping();

  @PutMapping
  ResponseEntity putMapping();

  @DeleteMapping
  ResponseEntity deleteMapping();

  @PatchMapping
  ResponseEntity patchMapping();

  @RequestMapping(Config.TESTMETHOD_URL)
  ResponseEntity testMethod();

  ResponseEntity noRequestMappingAnnotation();

}
