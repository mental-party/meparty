package com.teammental.mehelper;

import java.util.Calendar;
import java.util.Date;

public class DateHelper {

  /**
   * Creates a new {@link Date} object.
   * @param year year
   * @param month month
   * @param day day
   * @return Date
   */
  public static Date newDate(int year, int month, int day) {

    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, year);
    cal.set(Calendar.MONTH, month);
    cal.set(Calendar.DAY_OF_MONTH, day);
    return cal.getTime();
  }
}
