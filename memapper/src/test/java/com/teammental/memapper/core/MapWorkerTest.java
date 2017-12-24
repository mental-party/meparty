package com.teammental.memapper.core;

import com.teammental.memapper.configuration.MapConfiguration;
import com.teammental.memapper.configuration.MapConfigurationBuilder;
import com.teammental.memapper.configuration.MapConfigurationRegistry;
import com.teammental.memapper.to.TargetPersonTo;
import com.teammental.memapper.to.TeacherPersonTo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by sa on 12/23/2017.
 */
public class MapWorkerTest {

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
    TeacherPersonTo teacherPersonTo = new TeacherPersonTo();
    teacherPersonTo.setId(1);
    teacherPersonTo.setTitle("title");
    teacherPersonTo.setTitle2("title2");

    TargetPersonTo targetPersonTo = new TargetPersonTo();
    MapWorker worker = new MapWorker(teacherPersonTo, targetPersonTo);
    targetPersonTo = (TargetPersonTo) worker.map();

    assertEquals(teacherPersonTo.getId(), targetPersonTo.getId());
    assertEquals(teacherPersonTo.getTitle(), targetPersonTo.getTitle2());
    assertEquals(teacherPersonTo.getTitle2(), targetPersonTo.getTitle());
  }
}