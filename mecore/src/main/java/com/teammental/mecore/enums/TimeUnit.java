package com.teammental.mecore.enums;

/**
 * Time units.
 *
 * @since 1.4.
 */
public enum TimeUnit {

  SECOND(1, "second"),
  MINUTE(2, "minute"),
  HOUR(3, "hour"),
  DAY(4, "day"),
  WEEK(5, "week"),
  MONTH(6, "month"),
  YEAR(7, "year"),
  DECADE(8, "decade"),
  CENTURY(9, "century"),
  MILLENNIUM(10, "millennium");

  private static String messageCodePrefix = "timeunit.";
  private int timeUnitId;
  private String messageCode;

  TimeUnit(int timeUnitId, String messageCode) {

    this.timeUnitId = timeUnitId;
    this.messageCode = messageCode;
  }

  /**
   * Get time unit id.
   *
   * @return time unit id
   */
  public int getTimeUnitId() {

    return timeUnitId;
  }

  /**
   * Get message code.
   *
   * @return message code
   */
  public String getMessageCode() {

    return messageCodePrefix + messageCode;
  }
}
