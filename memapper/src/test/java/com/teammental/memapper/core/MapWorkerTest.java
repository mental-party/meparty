package com.teammental.memapper.core;

import com.teammental.memapper.configuration.MapConfiguration;
import com.teammental.memapper.configuration.MapConfigurationBuilder;
import com.teammental.memapper.configuration.MapConfigurationRegistry;
import com.teammental.memapper.to.TargetPersonTo;
import com.teammental.memapper.to.TeacherPersonTo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by sa on 12/23/2017.
 */
@RunWith(Enclosed.class)
public class MapWorkerTest {


  public static class TwoWayMapping {

    @Before
    public void setUp() {

      MapConfiguration configuration = MapConfigurationBuilder
          .twoWayMapping()
          .between(TeacherPersonTo.class)
          .and(TargetPersonTo.class)
          .mapField("id")
          .with("id")
          .mapField("title")
          .with("title2")
          .mapField("title2")
          .with("title")
          .build();

      MapConfigurationRegistry registry = new MapConfigurationRegistry();
      registry.register(configuration);

      MapConfigurationRegistrySingleton.setRegistry(registry);
    }

    @Test
    public void shouldMapAllFields() {
      TeacherPersonTo teacherPersonTo = prepareTeacherDto();

      TargetPersonTo targetPersonTo = new TargetPersonTo();
      MapWorker worker = new MapWorker(teacherPersonTo, targetPersonTo);
      targetPersonTo = (TargetPersonTo) worker.map();

      assertEquals(teacherPersonTo.getId(), targetPersonTo.getId());
      assertEquals(teacherPersonTo.getTitle(), targetPersonTo.getTitle2());
      assertEquals(teacherPersonTo.getTitle2(), targetPersonTo.getTitle());
    }

    @Test
    public void shouldMapAllFields_whenReverse() {
      TargetPersonTo targetPersonTo = prepareTargetPersonTo();

      TeacherPersonTo teacherPersonTo = new TeacherPersonTo();
      MapWorker worker = new MapWorker(targetPersonTo, teacherPersonTo);
      teacherPersonTo = (TeacherPersonTo) worker.map();

      assertEquals(targetPersonTo.getId(), teacherPersonTo.getId());
      assertEquals(targetPersonTo.getTitle(), teacherPersonTo.getTitle2());
      assertEquals(targetPersonTo.getTitle2(), teacherPersonTo.getTitle());
    }

    @After
    public void cleanUp() {
      MapConfigurationRegistrySingleton.clean();
    }
  }

  public static class OneWayMapping {

    @Before
    public void setUp() {

      MapConfiguration configuration = MapConfigurationBuilder
          .oneWayMapping()
          .between(TeacherPersonTo.class)
          .and(TargetPersonTo.class)
          .mapField("id")
          .with("id")
          .mapField("title")
          .with("title2")
          .mapField("title2")
          .with("title")
          .build();

      MapConfigurationRegistry registry = new MapConfigurationRegistry();
      registry.register(configuration);

      MapConfigurationRegistrySingleton.setRegistry(registry);
    }

    @Test
    public void shouldMapAllFields() {

      TeacherPersonTo teacherPersonTo = prepareTeacherDto();

      TargetPersonTo targetPersonTo = new TargetPersonTo();
      MapWorker worker = new MapWorker(teacherPersonTo, targetPersonTo);
      targetPersonTo = (TargetPersonTo) worker.map();

      assertEquals(teacherPersonTo.getId(), targetPersonTo.getId());
      assertEquals(teacherPersonTo.getTitle(), targetPersonTo.getTitle2());
      assertEquals(teacherPersonTo.getTitle2(), targetPersonTo.getTitle());
    }

    @Test
    public void shouldNotMapAllFields_whenReverse() {

      TargetPersonTo targetPersonTo = prepareTargetPersonTo();

      TeacherPersonTo teacherPersonTo = new TeacherPersonTo();
      MapWorker worker = new MapWorker(targetPersonTo, teacherPersonTo);
      teacherPersonTo = (TeacherPersonTo) worker.map();

      assertEquals(targetPersonTo.getId(), teacherPersonTo.getId());
      assertNotEquals(targetPersonTo.getTitle(), teacherPersonTo.getTitle2());
      assertNotEquals(targetPersonTo.getTitle2(), teacherPersonTo.getTitle());
    }

    @After
    public void cleanUp() {
      MapConfigurationRegistrySingleton.clean();
    }
  }

  static TeacherPersonTo prepareTeacherDto() {
    TeacherPersonTo teacherPersonTo = new TeacherPersonTo();
    teacherPersonTo.setId(1);
    teacherPersonTo.setTitle("title");
    teacherPersonTo.setTitle2("title2");
    return teacherPersonTo;
  }

  static TargetPersonTo prepareTargetPersonTo() {
    TargetPersonTo targetPersonTo = new TargetPersonTo();
    targetPersonTo.setId(1);
    targetPersonTo.setTitle("title");
    targetPersonTo.setTitle2("title2");
    return targetPersonTo;
  }
}