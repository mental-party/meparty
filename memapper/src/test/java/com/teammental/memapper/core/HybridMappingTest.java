package com.teammental.memapper.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.teammental.memapper.configuration.MapConfiguration;
import com.teammental.memapper.configuration.MapConfigurationBuilder;
import com.teammental.memapper.configuration.MapConfigurationRegistry;
import com.teammental.memapper.to.EnumGender;
import com.teammental.memapper.to.NameTo;
import com.teammental.memapper.to.PersonTo;
import com.teammental.memapper.to.TargetPersonTo;
import org.junit.After;
import org.junit.Test;

public class HybridMappingTest {

  @Test
  public void shouldMapJustConfigured_whenDisabledHybridMapping() {

    MapConfiguration configuration = MapConfigurationBuilder
        .twoWayMapping()
        .disableHybridMapping()
        .between(PersonTo.class)
        .and(TargetPersonTo.class)
        .mapField("id")
        .with("id")
        .mapField("title")
        .with("title")
        .build();

    MapConfigurationRegistry registry = MapConfigurationRegistrySingleton.getSingleton();
    registry.register(configuration);


    PersonTo source = new PersonTo();
    source.setId(5);
    source.setName(new NameTo("John", "Doe"));
    source.setGender(EnumGender.MALE);
    source.setTitle("Title");
    source.setTitle2("title2");

    TargetPersonTo target = new TargetPersonTo();

    MapWorker worker = new MapWorker(source, target);
    target = (TargetPersonTo) worker.map();

    assertNotEquals(source.getName(), target.getName());
    assertNotEquals(source.getGender(), target.getGender());
    assertNotEquals(source.getTitle2(), target.getTitle2());
  }

  @Test
  public void shouldMapAsHybrid_whenEnabledHybridMapping() {

    MapConfiguration configuration = MapConfigurationBuilder
        .twoWayMapping()
        .enableHybridMapping()
        .between(PersonTo.class)
        .and(TargetPersonTo.class)
        .mapField("id")
        .with("id")
        .mapField("title")
        .with("title")
        .build();

    MapConfigurationRegistry registry = MapConfigurationRegistrySingleton.getSingleton();
    registry.register(configuration);


    PersonTo source = new PersonTo();
    source.setId(5);
    source.setName(new NameTo("John", "Doe"));
    source.setGender(EnumGender.MALE);
    source.setTitle("Title");

    TargetPersonTo target = new TargetPersonTo();

    MapWorker worker = new MapWorker(source, target);
    target = (TargetPersonTo) worker.map();

    assertEquals(source.getName(), target.getName());
    assertEquals(source.getGender(), target.getGender());
    assertEquals(source.getTitle2(), target.getTitle2());
  }


  @Test
  public void shouldMapAsHybrid_butNotOverrideConfigured_whenEnabledHybridMapping() {

    MapConfiguration configuration = MapConfigurationBuilder
        .twoWayMapping()
        .enableHybridMapping()
        .between(PersonTo.class)
        .and(TargetPersonTo.class)
        .mapField("id")
        .with("id")
        .mapField("title")
        .with("title2")
        .build();

    MapConfigurationRegistry registry = MapConfigurationRegistrySingleton.getSingleton();
    registry.register(configuration);


    PersonTo source = new PersonTo();
    source.setId(5);
    source.setName(new NameTo("John", "Doe"));
    source.setGender(EnumGender.MALE);
    source.setTitle("Title");

    TargetPersonTo target = new TargetPersonTo();

    MapWorker worker = new MapWorker(source, target);
    target = (TargetPersonTo) worker.map();

    assertNotEquals(source.getTitle2(), target.getTitle2());
    assertEquals(source.getTitle(), target.getTitle2());
  }

  @After
  public void clean() {
    MapConfigurationRegistrySingleton.clean();
  }

}
