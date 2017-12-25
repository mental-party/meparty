package com.teammental.mehelper;

import static org.junit.Assert.fail;

import org.junit.Test;

public class AssertHelperTest {

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentException_whenAnArgumentIsNull() {
    AssertHelper.notNull(4, null);
  }

  @Test
  public void shouldNotThrowException_whenNoNullArgument() {
    try {
      AssertHelper.notNull(43, "test");
    } catch (IllegalArgumentException ex) {
      fail();
    }
  }
}