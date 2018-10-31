package com.teammental.mecore.enums;

import java.util.Arrays;
import java.util.Optional;

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

    String messageCodePrefix = "timeunit.";
    return messageCodePrefix + messageCode;
  }

  /**
   * Resolve by timeUnitId.
   *
   * @param timeUnitId timeUnitId
   * @return optional of TimeUnit
   */
  public Optional<TimeUnit> resolve(Integer timeUnitId) {

    if (timeUnitId == null) {
      return Optional.empty();
    }

    return Arrays.stream(TimeUnit.values())
        .filter(timeUnit -> timeUnit.getTimeUnitId() == timeUnitId)
        .findFirst();
  }
}
