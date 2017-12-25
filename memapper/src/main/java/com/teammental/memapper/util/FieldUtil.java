package com.teammental.memapper.util;

import com.teammental.mehelper.AssertHelper;
import com.teammental.mehelper.PrimitiveHelper;
import com.teammental.mehelper.StringHelper;
import com.teammental.memapper.util.mapping.CommonMapUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by erhan.karakaya on 5/11/2017.
 */
public class FieldUtil {

  /**
   * Checks if a field's type is boolean.
   *
   * @param field Field to be checked.
   * @return true if the field's type is boolean.
   */
  public static boolean isBoolean(final Field field) {

    AssertHelper.notNull(field);

    Class<?> fieldType = field.getType();
    return fieldType.equals(boolean.class) || fieldType.equals(Boolean.class);
  }

  /**
   * Checks if two fields' types equals or are assignable from each other.
   *
   * @param firstField  first field
   * @param secondField second field
   * @return true if types of two field equals or are assignable.
   */
  public static boolean isConvertable(final Field firstField,
                                      final Field secondField) {

    AssertHelper.notNull(firstField, secondField);

    return firstField.getType().equals(secondField.getType())
        || firstField.getType().equals(PrimitiveHelper.getOppositeClass(secondField.getType()))
        || PrimitiveHelper.getOppositeClass(firstField.getType()).equals(secondField.getType())
        || firstField.getType().isAssignableFrom(secondField.getType())
        || secondField.getType().isAssignableFrom(firstField.getType());
  }

  /**
   * Gets a field of a given type.
   *
   * @param clazz the type which has the field.
   * @param name  field's name.
   * @return an Optional of Field
   */
  public static Optional<Field> getField(final Class<?> clazz, final String name) {

    AssertHelper.notNull(clazz, name);

    List<Field> fields = CommonMapUtil.getAllFields(clazz);

    return fields.stream()
        .filter(field -> field.getName().equals(name))
        .findFirst();
  }

  /**
   * Returns set method of the field if any public set method found.
   *
   * @param field field
   * @return set method.
   */
  public static Optional<Method> findSetMethod(final Field field) {

    AssertHelper.notNull(field);

    Class<?> declaringClass = field.getDeclaringClass();

    Method[] methods = declaringClass.getMethods();
    String capitalizedFieldName = StringHelper.capitalizeFirstLetter(field.getName());

    Optional<Method> optionalMethod = Arrays.stream(methods)
        .filter(m ->
            m.getName().equals("set" + capitalizedFieldName)
                && m.getParameterCount() == 1
                && (Arrays.stream(m.getParameterTypes()).anyMatch(p -> p.equals(field.getType())
                || PrimitiveHelper.getOppositeClass(p).equals(field.getType()))))
        .findFirst();

    return optionalMethod;
  }

  /**
   * Returns get method of the field if any public get method found.
   *
   * @param field field
   * @return get method.
   */
  public static Optional<Method> findGetMethod(final Field field) {

    AssertHelper.notNull(field);

    Class<?> declaringClass = field.getDeclaringClass();
    String capitalizedFieldName = StringHelper.capitalizeFirstLetter(field.getName());
    boolean isFieldBoolean = isBoolean(field);

    Method[] methods = declaringClass.getMethods();

    Optional<Method> optionalMethod = Arrays.stream(methods)
        .filter(m ->
            (m.getReturnType().equals(field.getType())
                || PrimitiveHelper.getOppositeClass(m.getReturnType()).equals(field.getType()))
                && (m.getName().equals("get" + capitalizedFieldName)
                || (isFieldBoolean && m.getName().equals("is" + capitalizedFieldName))))
        .findFirst();


    return optionalMethod;
  }

  /**
   * Examines if the declaring class of the field has
   * a public setter method for the field.
   *
   * @param field The field to be looked.
   * @return true if a public setter method found
   */
  public static boolean hasPublicSetMethod(Field field) {
    return findGetMethod(field).isPresent();
  }

  /**
   * Examines if the declaring class of the field has
   * a public getter method for the field.
   *
   * @param field The field to be looked.
   * @return true if a public setter method found
   */
  public static boolean hasPublicGetMethod(Field field) {
    return findGetMethod(field).isPresent();
  }
}
