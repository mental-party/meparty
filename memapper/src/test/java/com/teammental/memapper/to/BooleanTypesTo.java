package com.teammental.memapper.to;

/**
 * Created by erhan.karakaya on 5/11/2017.
 */
public class BooleanTypesTo {
  private boolean nameBeginsWithIsAndPrimitive;
  private boolean nameBeginsWithGetAndPrimitive;
  private Boolean nameBeginsWithIsAndWrapper;
  private Boolean nameBeginsWithGetAndWrapper;

  public boolean isNameBeginsWithIsAndPrimitive() {
    return nameBeginsWithIsAndPrimitive;
  }

  public void setNameBeginsWithIsAndPrimitive(boolean nameBeginsWithIsAndPrimitive) {
    this.nameBeginsWithIsAndPrimitive = nameBeginsWithIsAndPrimitive;
  }

  public boolean getNameBeginsWithGetAndPrimitive() {
    return nameBeginsWithGetAndPrimitive;
  }

  public void setNameBeginsWithGetAndPrimitive(boolean nameBeginsWithGetAndPrimitive) {
    this.nameBeginsWithGetAndPrimitive = nameBeginsWithGetAndPrimitive;
  }

  public Boolean isNameBeginsWithIsAndWrapper() {
    return nameBeginsWithIsAndWrapper;
  }

  public void setNameBeginsWithIsAndWrapper(Boolean nameBeginsWithIsAndWrapper) {
    this.nameBeginsWithIsAndWrapper = nameBeginsWithIsAndWrapper;
  }

  public Boolean getNameBeginsWithGetAndWrapper() {
    return nameBeginsWithGetAndWrapper;
  }

  public void setNameBeginsWithGetAndWrapper(Boolean nameBeginsWithGetAndWrapper) {
    this.nameBeginsWithGetAndWrapper = nameBeginsWithGetAndWrapper;
  }
}
