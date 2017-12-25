package com.teammental.memapper.to;

/**
 * Created by erhan.karakaya on 3/31/2017.
 */
public class PersonTo {
  private Integer id;
  private NameTo name;
  private EnumGender gender;
  private String title;
  private String title2;
  private String noSetter;
  private String notAccessibleSetter;
  private String noGetter;
  private String noAccessibleGetter;

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

  public String getTitle2() {

    return title2;
  }

  public void setTitle2(String title2) {

    this.title2 = title2;
  }

  public String getNoSetter() {

    return noSetter;
  }

  private void setNotAccessibleSetter(String notAccessibleSetter) {

    this.notAccessibleSetter = notAccessibleSetter;
  }

  public void setNoGetter(String noGetter) {

    this.noGetter = noGetter;
  }

  private String getNoAccessibleGetter() {

    return noAccessibleGetter;
  }

}
