package com.teammental.memapper.to;

/**
 * Created by erhan.karakaya on 3/31/2017.
 */
public class PersonTo {
  private Integer id;
  private NameTo name;
  private EnumGender gender;
  private String title;

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
}
