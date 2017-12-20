package com.teammental.mehandler.testapp;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.teammental.mecore.stereotype.dto.Dto;

public class TestDto implements Dto {

  @NotNull
  private int id;

  @Size(min = 5)
  private String name;

  public TestDto(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
