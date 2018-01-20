package com.teammental.merest.testapp;

import java.util.List;

import com.teammental.mecore.stereotype.controller.Controller;
import com.teammental.mecore.stereotype.controller.RestApi;
import com.teammental.merest.RestResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestApi(Config.TESTAPPLICATIONNAME)
@RequestMapping(Config.TESTRESTAPI_ROOTURL)
public interface TestRestApi extends Controller {

  @GetMapping
  RestResponse<List<TestDto>> getAll();

  @PostMapping
  RestResponse create(@RequestBody TestDto dto);

  @PutMapping
  RestResponse<Integer> update(TestDto dto);

  @RequestMapping("/{id}")
  RestResponse<TestDto> getById(@PathVariable("id") Integer id);

  @DeleteMapping("/{id}")
  RestResponse delete(@PathVariable("id") Integer id);
}