package com.teammental.memapper.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.teammental.memapper.to.BooleanTypesTo;
import com.teammental.memapper.to.PersonTo;
import com.teammental.memapper.to.PrimitiveTypeTo;
import com.teammental.memapper.to.WrapperTypeTo;
import com.teammental.memapper.util.mapping.CommonMapUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;


/**
 * Created by erhan.karakaya on 5/11/2017.
 */
@RunWith(Enclosed.class)
public class FieldUtilTest {


  public static class WhenFieldTypeIsBoolean {

    @Test
    public void shouldReturnTrue_whenFieldTypeIsPrimitive() {

      Optional<Field> primitiveBooleanField = CommonMapUtil.getAllFields(PrimitiveTypeTo.class)
          .stream().filter(field -> field.getType().equals(boolean.class)).findFirst();
      if (!primitiveBooleanField.isPresent()) {
        fail();
      }

      boolean isBoolean = FieldUtil.isBoolean(primitiveBooleanField.get());

      assertTrue(isBoolean);
    }

    @Test
    public void shouldReturnTrue_whenFieldTypeIsWrapper() {

      Optional<Field> wrapperBooleanField = CommonMapUtil.getAllFields(WrapperTypeTo.class)
          .stream().filter(field -> field.getType().equals(Boolean.class)).findFirst();
      if (!wrapperBooleanField.isPresent()) {
        fail();
      }

      boolean isBoolean = FieldUtil.isBoolean(wrapperBooleanField.get());

      assertTrue(isBoolean);
    }

    @Test
    public void shouldFindGetMethod_whenGetterMethodBeginsWithIs () {
      Optional<Field> field = FieldUtil.getField(BooleanTypesTo.class,
          "nameBeginsWithIsAndWrapper");

      boolean fieldHasPublicGetterMethod = FieldUtil.hasPublicGetMethod(field.get());

      assertTrue(fieldHasPublicGetterMethod);
    }

    @Test
    public void shouldFindGetMethod_whenGetterMethodBeginsWithGet () {
      Optional<Field> field = FieldUtil.getField(BooleanTypesTo.class,
          "nameBeginsWithGetAndPrimitive");

      boolean fieldHasPublicGetterMethod = FieldUtil.hasPublicGetMethod(field.get());

      assertTrue(fieldHasPublicGetterMethod);
    }

  }

  public static class WhenFieldTypeIsNotBoolean {

    @Test
    public void shouldReturnFalse() {

      Optional<Field> notBooleanField = CommonMapUtil.getAllFields(PrimitiveTypeTo.class)
          .stream().filter(field -> !field.getType().equals(boolean.class)).findAny();

      if (!notBooleanField.isPresent()) {
        fail();
      }

      boolean isBoolean = FieldUtil.isBoolean(notBooleanField.get());

      assertFalse(isBoolean);
    }
  }

  public static class WhenFieldIsNull {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentException() {

      Field nullField = null;

      FieldUtil.isBoolean(nullField);

    }
  }

  public static class FindField {

    @Test
    public void shouldReturnNotEmpty_whenFieldExists() {

      Optional<Field> fieldOptional = FieldUtil.getField(PersonTo.class, "id");
      assertTrue(fieldOptional.isPresent());
    }

    @Test
    public void shouldReturnEmpty_whenFieldDoesntExist() {

      Optional<Field> fieldOptional = FieldUtil.getField(PersonTo.class, "klfdjngofe");
      assertFalse(fieldOptional.isPresent());
    }
  }

  public static class FindSetMethod {

    @Test
    public void shouldReturnNotEmpty_whenMethodIsAccessible() {

      Optional<Field> fieldOptional = FieldUtil.getField(PersonTo.class, "id");
      Optional<Method> optional = FieldUtil.findSetMethod(fieldOptional.get());

      assertTrue(optional.isPresent());
    }

    @Test
    public void shouldReturnEmpty_whenMethodDoesntExist() {

      Optional<Field> fieldOptional = FieldUtil.getField(PersonTo.class, "noSetter");
      Optional<Method> optional = FieldUtil.findSetMethod(fieldOptional.get());

      assertFalse(optional.isPresent());
    }

    @Test
    public void shouldReturnEmpty_whenMethodIsNotAccessible() {

      Optional<Field> fieldOptional = FieldUtil.getField(PersonTo.class, "notAccessibleSetter");
      Optional<Method> optional = FieldUtil.findSetMethod(fieldOptional.get());

      assertFalse(optional.isPresent());
    }

    public static class FindGetMethod {

      @Test
      public void shouldReturnNotEmpty_whenMethodIsAccessible() {

        Optional<Field> fieldOptional = FieldUtil.getField(PersonTo.class, "id");
        Optional<Method> optional = FieldUtil.findGetMethod(fieldOptional.get());

        assertTrue(optional.isPresent());
      }

      @Test
      public void shouldReturnEmpty_whenMethodDoesntExist() {

        Optional<Field> fieldOptional = FieldUtil.getField(PersonTo.class, "noGetter");
        Optional<Method> optional = FieldUtil.findGetMethod(fieldOptional.get());

        assertFalse(optional.isPresent());
      }

      @Test
      public void shouldReturnEmpty_whenMethodIsNotAccessible() {

        Optional<Field> fieldOptional = FieldUtil.getField(PersonTo.class, "noAccessibleGetter");
        Optional<Method> optional = FieldUtil.findGetMethod(fieldOptional.get());

        assertFalse(optional.isPresent());
      }
    }
  }
}