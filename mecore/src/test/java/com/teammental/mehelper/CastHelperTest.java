package com.teammental.mehelper;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class CastHelperTest {

  public static class CastFromString {

    @Test
    public void shouldReturnNull_whenGivenTypeIsNull() {

      assertNull(CastHelper.castFromString("val", null));
    }

    @Test
    public void shouldReturnNull_whenGivenValueIsNull() {

      assertNull(CastHelper.castFromString(null, Boolean.class));
    }

    @Test
    public void shouldReturnNull_whenGivenValueIsEmpty() {

      assertNull(CastHelper.castFromString("", Boolean.class));
    }

    @Test
    public void shouldReturnNull_whenGivenTypeIsNotPrimitiveOrWrapper() {

      assertNull(CastHelper.castFromString("1", PrimitiveHelper.class));
    }
  }
}