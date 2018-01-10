package com.teammental.merest.testapp;

import com.teammental.mecore.stereotype.dto.Dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TestDto implements Dto {

  private Integer id;

  public TestDto(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public TestDto() {
  }

  @Size(min = 3)
  @NotNull
  private String name;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("{id:" + id + ", name:" + name + "}");
    return stringBuilder.toString();
  }
}
