package com.teammental.mehelper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * Created by erhan.karakaya on 8/25/2017.
 */
@RunWith(Enclosed.class)
public class PrimitiveHelperTest {
  public static class WhenPrimitive {

    @Test
    public void primitive_boolean_returns_class_bool() {
      Class<?> primitiveBooleanClass = boolean.class;

      Class<?> wrapperBooleanClass = PrimitiveHelper.getWrapperClass(primitiveBooleanClass);

      assertEquals(Boolean.class, wrapperBooleanClass);
    }

    @Test
    public void primitive_byte_returns_class_byte() {
      Class<?> primitiveByteClass = byte.class;

      Class<?> wrapperByteClass = PrimitiveHelper.getWrapperClass(primitiveByteClass);

      assertEquals(Byte.class, wrapperByteClass);
    }

    @Test
    public void primitive_short_returns_class_short() {
      Class<?> primitiveShortClass = short.class;

      Class<?> wrapperShortClass = PrimitiveHelper.getWrapperClass(primitiveShortClass);

      assertEquals(Short.class, wrapperShortClass);
    }

    @Test
    public void primitive_char_returns_class_charachter() {
      Class<?> primitiveCharClass = char.class;

      Class<?> wrapperCharachterClass = PrimitiveHelper.getWrapperClass(primitiveCharClass);

      assertEquals(Character.class, wrapperCharachterClass);
    }

    @Test
    public void primitive_int_returns_class_integer() {
      Class<?> primitiveIntClass = int.class;

      Class<?> wrapperIntegerClass = PrimitiveHelper.getWrapperClass(primitiveIntClass);

      assertEquals(Integer.class, wrapperIntegerClass);
    }

    @Test
    public void primitive_long_returns_class_long() {
      Class<?> primitiveLongClass = long.class;

      Class<?> wrapperLongClass = PrimitiveHelper.getWrapperClass(primitiveLongClass);

      assertEquals(Long.class, wrapperLongClass);
    }

    @Test
    public void primitive_float_returns_class_float() {
      Class<?> primitiveFloatClass = float.class;

      Class<?> wrapperFloatClass = PrimitiveHelper.getWrapperClass(primitiveFloatClass);

      assertEquals(Float.class, wrapperFloatClass);
    }

    @Test
    public void primitive_double_returns_class_double() {
      Class<?> primitiveDoubleClass = double.class;

      Class<?> wrapperDoubleClass = PrimitiveHelper.getWrapperClass(primitiveDoubleClass);

      assertEquals(Double.class, wrapperDoubleClass);
    }

    @Test
    public void primitive_void_returns_class_void() {
      Class<?> primitiveVoidClass = void.class;

      Class<?> wrapperVoidClass = PrimitiveHelper.getWrapperClass(primitiveVoidClass);

      assertEquals(Void.class, wrapperVoidClass);
    }

  }

  public static class WhenWrapper {

    @Test
    public void class_boolean_returns_primitive_bool() {
      Class<?> wrapperBooleanClass = Boolean.class;

      Class<?> primitiveBooleanClass = PrimitiveHelper.getPrimitiveClass(wrapperBooleanClass);

      assertEquals(boolean.class, primitiveBooleanClass);
    }


    @Test
    public void class_byte_returns_primitive_byte() {
      Class<?> wrapperByteClass = Byte.class;

      Class<?> primitiveByteClass = PrimitiveHelper.getPrimitiveClass(wrapperByteClass);

      assertEquals(byte.class, primitiveByteClass);
    }

    @Test
    public void class_short_returns_primitive_short() {
      Class<?> wrapperShortClass = Short.class;

      Class<?> primitiveShortClass = PrimitiveHelper.getPrimitiveClass(wrapperShortClass);

      assertEquals(short.class, primitiveShortClass);
    }

    @Test
    public void class_charachter_returns_primitive_char() {
      Class<?> wrapperCharachterClass = Character.class;

      Class<?> primitiveCharClass = PrimitiveHelper.getPrimitiveClass(wrapperCharachterClass);

      assertEquals(char.class, primitiveCharClass);
    }

    @Test
    public void class_integer_returns_primitive_int() {
      Class<?> wrapperIntegerClass = Integer.class;

      Class<?> primitiveIntClass = PrimitiveHelper.getPrimitiveClass(wrapperIntegerClass);

      assertEquals(int.class, primitiveIntClass);
    }

    @Test
    public void class_long_returns_primitive_long() {
      Class<?> wrapperLongClass = Long.class;

      Class<?> primitiveLongClass = PrimitiveHelper.getPrimitiveClass(wrapperLongClass);

      assertEquals(long.class, primitiveLongClass);
    }

    @Test
    public void class_float_returns_primitive_float() {
      Class<?> wrapperFloatClass = Float.class;

      Class<?> primitiveFloatClass = PrimitiveHelper.getPrimitiveClass(wrapperFloatClass);

      assertEquals(float.class, primitiveFloatClass);
    }

    @Test
    public void class_double_returns_primitive_double() {
      Class<?> wrapperDoubleClass = Double.class;

      Class<?> primitiveDoubleClass = PrimitiveHelper.getPrimitiveClass(wrapperDoubleClass);

      assertEquals(double.class, primitiveDoubleClass);
    }

    @Test
    public void class_void_returns_primitive_void() {
      Class<?> wrapperVoidClass = Void.class;

      Class<?> primitiveVoidClass = PrimitiveHelper.getPrimitiveClass(wrapperVoidClass);

      assertEquals(void.class, primitiveVoidClass);
    }

  }

  public static class WhenNotKnown {

    @Test
    public void primitive_boolean_returns_class_bool() {
      Class<?> primitiveBooleanClass = boolean.class;

      Class<?> wrapperBooleanClass = PrimitiveHelper.getOppositeClass(primitiveBooleanClass);

      assertEquals(Boolean.class, wrapperBooleanClass);
    }

    @Test
    public void primitive_byte_returns_class_byte() {
      Class<?> primitiveByteClass = byte.class;

      Class<?> wrapperByteClass = PrimitiveHelper.getOppositeClass(primitiveByteClass);

      assertEquals(Byte.class, wrapperByteClass);
    }

    @Test
    public void primitive_short_returns_class_short() {
      Class<?> primitiveShortClass = short.class;

      Class<?> wrapperShortClass = PrimitiveHelper.getOppositeClass(primitiveShortClass);

      assertEquals(Short.class, wrapperShortClass);
    }

    @Test
    public void primitive_char_returns_class_charachter() {
      Class<?> primitiveCharClass = char.class;

      Class<?> wrapperCharachterClass = PrimitiveHelper.getOppositeClass(primitiveCharClass);

      assertEquals(Character.class, wrapperCharachterClass);
    }

    @Test
    public void primitive_int_returns_class_integer() {
      Class<?> primitiveIntClass = int.class;

      Class<?> wrapperIntegerClass = PrimitiveHelper.getOppositeClass(primitiveIntClass);

      assertEquals(Integer.class, wrapperIntegerClass);
    }

    @Test
    public void primitive_long_returns_class_long() {
      Class<?> primitiveLongClass = long.class;

      Class<?> wrapperLongClass = PrimitiveHelper.getOppositeClass(primitiveLongClass);

      assertEquals(Long.class, wrapperLongClass);
    }

    @Test
    public void primitive_float_returns_class_float() {
      Class<?> primitiveFloatClass = float.class;

      Class<?> wrapperFloatClass = PrimitiveHelper.getOppositeClass(primitiveFloatClass);

      assertEquals(Float.class, wrapperFloatClass);
    }

    @Test
    public void primitive_double_returns_class_double() {
      Class<?> primitiveDoubleClass = double.class;

      Class<?> wrapperDoubleClass = PrimitiveHelper.getOppositeClass(primitiveDoubleClass);

      assertEquals(Double.class, wrapperDoubleClass);
    }

    @Test
    public void primitive_void_returns_class_void() {
      Class<?> primitiveVoidClass = void.class;

      Class<?> wrapperVoidClass = PrimitiveHelper.getOppositeClass(primitiveVoidClass);

      assertEquals(Void.class, wrapperVoidClass);
    }

    @Test
    public void class_boolean_returns_primitive_bool() {
      Class<?> wrapperBooleanClass = Boolean.class;

      Class<?> primitiveBooleanClass = PrimitiveHelper.getPrimitiveClass(wrapperBooleanClass);

      assertEquals(boolean.class, primitiveBooleanClass);
    }


    @Test
    public void class_byte_returns_primitive_byte() {
      Class<?> wrapperByteClass = Byte.class;

      Class<?> primitiveByteClass = PrimitiveHelper.getOppositeClass(wrapperByteClass);

      assertEquals(byte.class, primitiveByteClass);
    }

    @Test
    public void class_short_returns_primitive_short() {
      Class<?> wrapperShortClass = Short.class;

      Class<?> primitiveShortClass = PrimitiveHelper.getOppositeClass(wrapperShortClass);

      assertEquals(short.class, primitiveShortClass);
    }

    @Test
    public void class_charachter_returns_primitive_char() {
      Class<?> wrapperCharachterClass = Character.class;

      Class<?> primitiveCharClass = PrimitiveHelper.getOppositeClass(wrapperCharachterClass);

      assertEquals(char.class, primitiveCharClass);
    }

    @Test
    public void class_integer_returns_primitive_int() {
      Class<?> wrapperIntegerClass = Integer.class;

      Class<?> primitiveIntClass = PrimitiveHelper.getOppositeClass(wrapperIntegerClass);

      assertEquals(int.class, primitiveIntClass);
    }

    @Test
    public void class_long_returns_primitive_long() {
      Class<?> wrapperLongClass = Long.class;

      Class<?> primitiveLongClass = PrimitiveHelper.getOppositeClass(wrapperLongClass);

      assertEquals(long.class, primitiveLongClass);
    }

    @Test
    public void class_float_returns_primitive_float() {
      Class<?> wrapperFloatClass = Float.class;

      Class<?> primitiveFloatClass = PrimitiveHelper.getOppositeClass(wrapperFloatClass);

      assertEquals(float.class, primitiveFloatClass);
    }

    @Test
    public void class_double_returns_primitive_double() {
      Class<?> wrapperDoubleClass = Double.class;

      Class<?> primitiveDoubleClass = PrimitiveHelper.getOppositeClass(wrapperDoubleClass);

      assertEquals(double.class, primitiveDoubleClass);
    }

    @Test
    public void class_void_returns_primitive_void() {
      Class<?> wrapperVoidClass = Void.class;

      Class<?> primitiveVoidClass = PrimitiveHelper.getOppositeClass(wrapperVoidClass);

      assertEquals(void.class, primitiveVoidClass);
    }
  }

  public static class IsPrimitiveOrWrapper {

    @Test
    public void shouldReturnTrue_whenTypeIsWrapper() {
      assertTrue(PrimitiveHelper.isWrapperOrPrimitive(Integer.class));
    }

    @Test
    public void shouldReturnTrue_whenTypeIsPrimitive() {
      assertTrue(PrimitiveHelper.isWrapperOrPrimitive(int.class));
    }

    @Test
    public void shouldReturnFalse_whenTypeIsNotPrimitiveOrWrapper() {
      assertFalse(PrimitiveHelper
      .isWrapperOrPrimitive(PrimitiveHelperTest.class));
    }
  }

}