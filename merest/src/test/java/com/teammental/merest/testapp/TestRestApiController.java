package com.teammental.merest.testapp;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestApiController implements TestRestApi {
  @Override
  public ResponseEntity<List<TestDto>> getAll() {

    TestDto testDto1 = new TestDto(1, "1");
    TestDto testDto2 = new TestDto(2, "2");

    List<TestDto> list = Arrays.asList(testDto1, testDto2);

    return ResponseEntity.ok(list);
  }

  @Override
  public ResponseEntity create(TestDto dto) {

    dto.setId(3);

    return ResponseEntity.status(HttpStatus.CREATED)
        .header("Location", Config.TESTRESTAPI_ROOTURL + "/id")
        .build();
  }

  @Override
  public ResponseEntity<Integer> update(TestDto dto) {
    return ResponseEntity.badRequest().build();
  }

  @Override
  public ResponseEntity<TestDto> getById(@PathVariable Integer id) {
    if (id == 1) {
      TestDto testDto = new TestDto(1, "name");
      return ResponseEntity.ok(testDto);
    }
    return ResponseEntity.notFound().build();
  }

  @Override
  public ResponseEntity delete(Integer id) {
    return ResponseEntity.noContent().build();
  }
}
