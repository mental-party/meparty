package com.teammental.mecore.enums;

public enum FontType {
  ARIAL("Arial"),
  TIMES_NEW_ROMAN("Times New Roman");

  private String text;

  FontType(String text) {

    this.text = text;
  }


  @Override
  public String toString() {

    return text;
  }
}
