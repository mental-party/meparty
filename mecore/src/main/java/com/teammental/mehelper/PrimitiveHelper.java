package com.teammental.mehelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by erhan.karakaya on 8/25/2017.
 */
public class PrimitiveHelper {
  private static final Map<Class<?>, Class<?>> primitiveMap = new HashMap<>();
  private static final Map<Class<?>, Class<?>> wrapperMap = new HashMap<>();

  static {
    primitiveMap.put(boolean.class, Boolean.class);
    primitiveMap.put(byte.class, Byte.class);
    primitiveMap.put(short.class, Short.class);
    primitiveMap.put(char.class, Character.class);
    primitiveMap.put(int.class, Integer.class);
    primitiveMap.put(long.class, Long.class);
    primitiveMap.put(float.class, Float.class);
    primitiveMap.put(double.class, Double.class);
    primitiveMap.put(void.class, Void.class);

    wrapperMap.put(Boolean.class, boolean.class);
    wrapperMap.put(Byte.class, byte.class);
    wrapperMap.put(Short.class, short.class);
    wrapperMap.put(Character.class, char.class);
    wrapperMap.put(Integer.class, int.class);
    wrapperMap.put(Long.class, long.class);
    wrapperMap.put(Float.class, float.class);
    wrapperMap.put(Double.class, double.class);
    wrapperMap.put(Void.class, void.class);
  }

  /**
   * Gets the Wrapper class of the given primitive type.
   *
   * @param primitiveClass the primitive type argument.
   * @return Wrapper class type.
   */
  public static Class<?> getWrapperClass(Class<?> primitiveClass) {
    AssertHelper.notNull(primitiveClass);

    if (primitiveClass.isPrimitive()) {
      Class<?> wrapper = primitiveMap.get(primitiveClass);
      return wrapper;
    }
    return primitiveClass;
  }

  /**
   * Gets the primitive class of the given Wrapper class, if there is.
   *
   * @param wrapperClass the wrapper type argument.
   * @return primitive class type.
   */
  public static Class<?> getPrimitiveClass(Class<?> wrapperClass) {
    AssertHelper.notNull(wrapperClass);

    if (wrapperClass.isPrimitive()) {
      return wrapperClass;
    }

    Class<?> primitive = wrapperMap.get(wrapperClass);
    if (primitive == null) {
      return wrapperClass;
    }

    return primitive;
  }

  /**
   * Gets the primitive or wrapper class type of the given type.
   * If none found, returns clazz itself.
   *
   * @param clazz class type.
   * @return primitive or wrapper class of the given type.
   */
  public static Class<?> getOppositeClass(Class<?> clazz) {

    AssertHelper.notNull(clazz);

    Class<?> oppositeClass;

    if (clazz.isPrimitive()) {
      oppositeClass = primitiveMap.get(clazz);
    } else {
      oppositeClass = wrapperMap.get(clazz);
      if (oppositeClass == null) {
        oppositeClass = clazz;
      }
    }
    return oppositeClass;
  }

  /**
   * Checks if the given clazz is wrapper or primitive.
   * @param clazz
   * @return
   */
  public static boolean isWrapperOrPrimitive(Class<?> clazz) {
    return wrapperMap.containsKey(clazz)
        || primitiveMap.containsKey(clazz);
  }
}