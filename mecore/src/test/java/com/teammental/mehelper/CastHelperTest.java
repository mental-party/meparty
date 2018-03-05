package com.teammental.mehelper;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class CastHelperTest {

  @RunWith(Enclosed.class)
  public static class CastFromString {

    public static class NullCheck {

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

    public static class CastToBoolean {

      @Test
      public void shouldReturnBoolean_whenGivenValueIsCastable() {

        final Boolean expectedVal = true;
        final String val = expectedVal.toString();

        Boolean actualVal = CastHelper.castFromString(val, Boolean.class);

        assertEquals(expectedVal, actualVal);
      }

      @Test
      public void shouldReturnNull_whenGivenValueIsNotCastable() {

        final Boolean expectedVal = null;
        final String val = "no-bool";

        Boolean actualVal = CastHelper.castFromString(val, Boolean.class);

        assertEquals(expectedVal, actualVal);
      }
    }

    public static class CastToByte {

      @Test
      public void shouldReturnByte_whenGivenValueIsCastable() {

        final Byte expectedVal = Byte.MAX_VALUE;
        final String val = expectedVal.toString();

        Byte actualVal = CastHelper.castFromString(val, Byte.class);

        assertEquals(expectedVal, actualVal);
      }

      @Test
      public void shouldReturnNull_whenGivenValueIsNotCastable() {

        final Byte expectedVal = null;
        final String val = "no-byte";

        Byte actualVal = CastHelper.castFromString(val, Byte.class);

        assertEquals(expectedVal, actualVal);
      }
    }

    public static class CastToShort {

      @Test
      public void shouldReturnShort_whenGivenValueIsCastable() {

        final Short expectedVal = Short.MAX_VALUE;
        final String val = expectedVal.toString();

        Short actualVal = CastHelper.castFromString(val, Short.class);

        assertEquals(expectedVal, actualVal);
      }

      @Test
      public void shouldReturnNull_whenGivenValueIsNotCastable() {

        final Short expectedVal = null;
        final String val = "no-short";

        Short actualVal = CastHelper.castFromString(val, Short.class);

        assertEquals(expectedVal, actualVal);
      }
    }

    public static class CastToCharacter {

      @Test
      public void shouldReturnCharacter_whenGivenValueIsCastable() {

        final Character expectedVal = Character.MAX_VALUE;
        final String val = expectedVal.toString();

        Character actualVal = CastHelper.castFromString(val, Character.class);

        assertEquals(expectedVal, actualVal);
      }

      @Test
      public void shouldReturnNull_whenGivenValueIsNotCastable() {

        final Character expectedVal = null;
        final String val = "";

        Character actualVal = CastHelper.castFromString(val, Character.class);

        assertEquals(expectedVal, actualVal);
      }
    }



    public static class CastToInteger {

      @Test
      public void shouldReturnInteger_whenGivenValueIsCastable() {

        final Integer expectedVal = 53543;
        final String val = expectedVal.toString();

        Integer actualVal = CastHelper.castFromString(val, Integer.class);

        assertEquals(expectedVal, actualVal);
      }

      @Test
      public void shouldReturnNull_whenGivenValueIsNotCastable() {

        final Integer expectedVal = null;
        final String val = "no-int";

        Integer actualVal = CastHelper.castFromString(val, Integer.class);

        assertEquals(expectedVal, actualVal);
      }
    }

    public static class CastToLong {

      @Test
      public void shouldReturnLong_whenGivenValueIsCastable() {

        final Long expectedVal = Long.MAX_VALUE;
        final String val = expectedVal.toString();

        Long actualVal = CastHelper.castFromString(val, Long.class);

        assertEquals(expectedVal, actualVal);
      }

      @Test
      public void shouldReturnNull_whenGivenValueIsNotCastable() {

        final Long expectedVal = null;
        final String val = "no-long";

        Long actualVal = CastHelper.castFromString(val, Long.class);

        assertEquals(expectedVal, actualVal);
      }
    }

    public static class CastToFloat {

      @Test
      public void shouldReturnLong_whenGivenValueIsCastable() {

        final Float expectedVal = Float.MAX_VALUE;
        final String val = expectedVal.toString();

        Float actualVal = CastHelper.castFromString(val, Float.class);

        assertEquals(expectedVal, actualVal);
      }

      @Test
      public void shouldReturnNull_whenGivenValueIsNotCastable() {

        final Float expectedVal = null;
        final String val = "no-long";

        Float actualVal = CastHelper.castFromString(val, Float.class);

        assertEquals(expectedVal, actualVal);
      }
    }

    public static class CastToDouble {

      @Test
      public void shouldReturnDouble_whenGivenValueIsCastable() {

        final Double expectedVal = Double.MAX_VALUE;
        final String val = expectedVal.toString();

        Double actualVal = CastHelper.castFromString(val, Double.class);

        assertEquals(expectedVal, actualVal);
      }

      @Test
      public void shouldReturnNull_whenGivenValueIsNotCastable() {

        final Double expectedVal = null;
        final String val = "no-long";

        Double actualVal = CastHelper.castFromString(val, Double.class);

        assertEquals(expectedVal, actualVal);
      }
    }
  }
}