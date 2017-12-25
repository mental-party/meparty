package com.teammental.memapper.util.mapping;

import static org.junit.Assert.assertEquals;

import com.teammental.memapper.to.EnumGender;
import com.teammental.memapper.to.NameTo;
import com.teammental.memapper.to.TeacherPersonTo;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;


/**
 * Created by erhan.karakaya on 3/31/2017.
 */

public class CommonMapUtilTest {


  @Test
  public void shouldIncludeSuperFields_whenIncludeSuperFieldsIsTrue() {
    final boolean includeSuperfields = true;

    List<Field> fields = CommonMapUtil.getAllFields(TeacherPersonTo.class, includeSuperfields);
    HashMap<String, Class<?>> actualFields = new HashMap<>();
    fields.forEach(field -> actualFields.put(field.getName(), field.getType()));

    HashMap<String, Class<?>> expectedFields =
        prepareHashMapOfFieldsInTeacherPersonTo(includeSuperfields);

    assertEquals(expectedFields, actualFields);
  }

  @Test
  public void shouldIncludeSuperFields_whenIncludeSuperFieldsIsNotGiven() {
    final boolean includeSuperfields = true;

    List<Field> fields = CommonMapUtil.getAllFields(TeacherPersonTo.class);
    HashMap<String, Class<?>> actualFields = new HashMap<>();
    fields.forEach(field -> actualFields.put(field.getName(), field.getType()));

    HashMap<String, Class<?>> expectedFields =
        prepareHashMapOfFieldsInTeacherPersonTo(includeSuperfields);

    assertEquals(expectedFields, actualFields);
  }

  @Test
  public void shouldNotIncludeSuperFields_whenIncludeSuperFieldsIsFalse() {
    final boolean includeSuperfields = false;

    List<Field> fields = CommonMapUtil.getAllFields(TeacherPersonTo.class, includeSuperfields);
    HashMap<String, Class<?>> actualFields = new HashMap<>();
    fields.forEach(field -> actualFields.put(field.getName(), field.getType()));

    HashMap<String, Class<?>> expectedFields =
        prepareHashMapOfFieldsInTeacherPersonTo(includeSuperfields);

    assertEquals(expectedFields, actualFields);
  }

  @Test
  public void shouldReturnFieldsAndTheirTypes_whenIncludeSuperFieldsIsFalse() {
    final boolean includeSuperFields = false;
    final Map<String, Class<?>> expectedMap = prepareHashMapOfFieldsInTeacherPersonTo(includeSuperFields);
    final Map<String, Class<?>> actualMap = CommonMapUtil
        .getFieldsTypeMap(TeacherPersonTo.class, includeSuperFields);

    assertEquals(expectedMap, actualMap);
  }

  private HashMap<String, Class<?>> prepareHashMapOfFieldsInTeacherPersonTo(
      boolean includeSuperfields) {
    HashMap<String, Class<?>> expectedFields = new HashMap<>();
    if (includeSuperfields) {
      expectedFields.put("id", Integer.class);
      expectedFields.put("title", String.class);
      expectedFields.put("name", NameTo.class);
      expectedFields.put("gender", EnumGender.class);
      expectedFields.put("title2", String.class);
      expectedFields.put("noSetter", String.class);
      expectedFields.put("notAccessibleSetter", String.class);
      expectedFields.put("noGetter", String.class);
      expectedFields.put("noAccessibleGetter", String.class);
    }
    expectedFields.put("lessons", List.class);
    expectedFields.put("salary", Double.class);
    return expectedFields;
  }


}