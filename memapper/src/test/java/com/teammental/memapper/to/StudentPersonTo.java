package com.teammental.memapper.to;

import java.util.List;

/**
 * Created by erhan.karakaya on 3/31/2017.
 */
public class StudentPersonTo extends PersonTo {
  private List<String> lessons;

  public List<String> getLessons() {
    return lessons;
  }

  public void setLessons(List<String> lessons) {
    this.lessons = lessons;
  }
}
