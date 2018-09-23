package com.teammental.memapper.to.abstractmapper;

import com.teammental.memapper.to.EnumGender;

public class SourceTo {

  private String no;
  private EnumGender enumGender;
  private String title;

  public String getNo() {

    return no;
  }

  public void setNo(String no) {

    this.no = no;
  }

  public EnumGender getEnumGender() {

    return enumGender;
  }

  public void setEnumGender(EnumGender enumGender) {

    this.enumGender = enumGender;
  }

  public String getTitle() {

    return title;
  }

  public void setTitle(String title) {

    this.title = title;
  }
}
