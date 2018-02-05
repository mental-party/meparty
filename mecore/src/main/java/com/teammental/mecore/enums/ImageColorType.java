package com.teammental.mecore.enums;

public enum ImageColorType {

  BLACK_WHITE(0),
  GRAYSCALE(1),
  RGB(2),
  CMYK(3),
  OTHER(-1),
  UNKNOWN(-2);

  private int value;

  ImageColorType(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
