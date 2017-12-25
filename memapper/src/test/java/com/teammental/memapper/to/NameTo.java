package com.teammental.memapper.to;

/**
 * Created by erhan.karakaya on 3/31/2017.
 */
public class NameTo {
  /**
   * Creates new instance of NameTo.
   *
   * @param firstName  firstname
   * @param middleName middlename
   * @param lastName   lastname
   */
  public NameTo(String firstName, String middleName, String lastName) {
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
  }

  public NameTo(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  private String firstName;
  private String middleName;
  private String lastName;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

}
