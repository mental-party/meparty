package com.teammental.merest.testapp;

import com.teammental.medto.FilterDto;
import com.teammental.merest.RestResponse;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestApiController implements TestRestApi {

  @Override
  public RestResponse<List<TestDto>> getAll() {

    TestDto testDto1 = new TestDto(1, "1");
    TestDto testDto2 = new TestDto(2, "2");

    List<TestDto> list = Arrays.asList(testDto1, testDto2);

    return RestResponse.of(ResponseEntity.ok(list));
  }

  @Override
  public RestResponse create(@Validated @RequestBody TestDto dto) {

    dto.setId(3);

    return RestResponse.of(ResponseEntity.status(HttpStatus.CREATED)
        .header("Location", Config.TESTRESTAPI_ROOTURL + "/3")
        .build());
  }

  @Override
  public RestResponse<Integer> update(TestDto dto) {

    return RestResponse.of(ResponseEntity.badRequest().build());
  }

  @Override
  public RestResponse<TestDto> getById(@PathVariable Integer id) {

    if (id == 1) {
      TestDto testDto = new TestDto(1, "name");
      ResponseEntity responseEntity = ResponseEntity.ok(testDto);
      return new RestResponse<TestDto>(responseEntity);
    }
    return new RestResponse<TestDto>(ResponseEntity.notFound().build());
  }

  @Override
  public RestResponse delete(Integer id) {

    return RestResponse.of(ResponseEntity.noContent().build());
  }

  @Override
  public RestResponse<Page<TestDto>> filterAll(@RequestBody(required = false)
                                                     FilterDto filterDto) {

    TestDto testDto1 = new TestDto(1, "1");
    TestDto testDto2 = new TestDto(2, "2");

    List<TestDto> list = Arrays.asList(testDto1, testDto2);

    Page<TestDto> page = new PageImpl<>(list, new PageRequest(0,2,
        Sort.by(Sort.Direction.ASC, "id")), 2);

    return RestResponse.of(ResponseEntity.ok(page));
  }

  @Override
  public RestResponse<TestDto> getOneGenericType(Integer id) {
    TestDto testDto = new TestDto(1,"name");

    return RestResponse.of(ResponseEntity.ok(testDto));
  }


  @Override
  public RestResponse<UnknownPropertyTo> getUnknownPropertyTo() {

    UnknownPropertyTo to = new UnknownPropertyTo();
    to.setId(1);
    return RestResponse.of(ResponseEntity.ok(to));
  }
}
