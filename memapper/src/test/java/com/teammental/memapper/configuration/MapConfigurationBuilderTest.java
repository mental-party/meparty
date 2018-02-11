package com.teammental.memapper.configuration;

import static org.junit.Assert.*;

import com.teammental.memapper.exception.FieldTypesAreNotAssignableException;
import com.teammental.memapper.exception.NoSuchFieldException;
import com.teammental.memapper.to.TargetPersonTo;
import com.teammental.memapper.to.TeacherPersonTo;
import org.junit.Test;

public class MapConfigurationBuilderTest {

  @Test
  public void shouldSuccess() {
    MapConfiguration configuration = MapConfigurationBuilder
        .oneWayMapping()
        .between(TeacherPersonTo.class)
        .and(TargetPersonTo.class)
        .mapField("id")
        .with("id")
        .mapField("title")
        .with("title2")
        .build();

    assertNotNull(configuration);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldFail_whenSourceClassTypeIsNull() {
    MapConfiguration configuration = MapConfigurationBuilder
        .oneWayMapping()
        .between(null)
        .and(TargetPersonTo.class)
        .mapField("id")
        .with("id")
        .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldFail_whenTargetClassTypeIsNull() {
    MapConfiguration configuration = MapConfigurationBuilder
        .oneWayMapping()
        .between(TeacherPersonTo.class)
        .and(null)
        .mapField("id")
        .with("id")
        .build();
  }

  @Test(expected = NoSuchFieldException.class)
  public void shouldFail_whenSourceFieldNotFound() {
    MapConfiguration configuration = MapConfigurationBuilder
        .oneWayMapping()
        .between(TeacherPersonTo.class)
        .and(TargetPersonTo.class)
        .mapField("mklvfdnkjndfr")
        .with("id")
        .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldFail_whenSourceFieldIsNull() {
    MapConfiguration configuration = MapConfigurationBuilder
        .oneWayMapping()
        .between(TeacherPersonTo.class)
        .and(TargetPersonTo.class)
        .mapField(null)
        .with("id")
        .build();
  }

  @Test(expected = NoSuchFieldException.class)
  public void shouldFail_whenTargetFieldNotFound() {
    MapConfiguration configuration = MapConfigurationBuilder
        .oneWayMapping()
        .between(TeacherPersonTo.class)
        .and(TargetPersonTo.class)
        .mapField("id")
        .with("fwsapouytre")
        .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldFail_whenTargetFieldIsNull() {
    MapConfiguration configuration = MapConfigurationBuilder
        .oneWayMapping()
        .between(TeacherPersonTo.class)
        .and(TargetPersonTo.class)
        .mapField("id")
        .with(null)
        .build();
  }

  @Test(expected = FieldTypesAreNotAssignableException.class)
  public void shouldFail_whenFieldTypesAreNotAssignable() {
    MapConfiguration configuration = MapConfigurationBuilder
        .oneWayMapping()
        .between(TeacherPersonTo.class)
        .and(TargetPersonTo.class)
        .mapField("id")
        .with("title")
        .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldFail_whenNoFieldMappingIsAdded() {
    MapConfiguration configuration = MapConfigurationBuilder
        .oneWayMapping()
        .between(TeacherPersonTo.class)
        .and(TargetPersonTo.class)
        .build();
  }

  @Test()
  public void shouldSetLevelTo1_whenGivenLevelIsZero() {
    MapConfiguration configuration = MapConfigurationBuilder
        .oneWayMapping()
        .depthLevel(0)
        .between(TeacherPersonTo.class)
        .and(TargetPersonTo.class)
        .mapField("id")
        .with("id")
        .build();

    assertEquals(1, configuration.getDepthLevel());
  }

  @Test()
  public void shouldSetLevelTo1_whenGivenLevelIsMinus() {
    MapConfiguration configuration = MapConfigurationBuilder
        .oneWayMapping()
        .depthLevel(-1)
        .between(TeacherPersonTo.class)
        .and(TargetPersonTo.class)
        .mapField("id")
        .with("id")
        .build();

    assertEquals(1, configuration.getDepthLevel());
  }

}