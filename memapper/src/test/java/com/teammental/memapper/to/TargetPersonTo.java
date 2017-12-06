package com.teammental.memapper.to;

import java.util.List;

/**
 * Created by erhan.karakaya on 4/3/2017.
 */
public class TargetPersonTo {
  private Integer id;
  private NameTo name;
  private EnumGender gender;
  private String title;
  private List<String> lessons;
  private Double salary;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public NameTo getName() {
    return name;
  }

  public void setName(NameTo name) {
    this.name = name;
  }

  public EnumGender getGender() {
    return gender;
  }

  public void setGender(EnumGender gender) {
    this.gender = gender;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<String> getLessons() {
    return lessons;
  }

  public void setLessons(List<String> lessons) {
    this.lessons = lessons;
  }

  public Double getSalary() {
    return salary;
  }

  public void setSalary(Double salary) {
    this.salary = salary;
  }
}
