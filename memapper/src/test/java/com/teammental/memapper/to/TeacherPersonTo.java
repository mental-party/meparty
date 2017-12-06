package com.teammental.memapper.to;

import java.util.List;

/**
 * Created by erhan.karakaya on 3/31/2017.
 */
public class TeacherPersonTo extends PersonTo {
  private List<String> lessons;
  private Double salary;

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
