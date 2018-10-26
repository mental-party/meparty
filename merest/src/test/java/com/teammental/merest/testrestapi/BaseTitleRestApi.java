package com.teammental.merest.testrestapi;

import com.teammental.medto.FilterDto;
import com.teammental.merest.RestResponse;
import com.teammental.merest.testapp.TestDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

public interface BaseTitleRestApi<T, S> {

  @GetMapping
  RestResponse<List<TestDto>> getAll();

  @PostMapping
  RestResponse create(@RequestBody TestDto dto);

  @PutMapping
  RestResponse<S> update(TestDto dto);

  @RequestMapping("/{id}")
  RestResponse<TestDto> getById(@PathVariable("id") Integer id);

  @DeleteMapping("/{id}")
  RestResponse delete(@PathVariable("id") Integer id);

  @PostMapping("/filter")
  RestResponse<Page<TestDto>> filterAll(@RequestBody(required = false)
                                            FilterDto filterDto);

  @RequestMapping("/getOne/{id}")
  RestResponse<T> getOneGenericType(@PathVariable("id") Integer id);
}
