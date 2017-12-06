package com.teammental.mebuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.teammental.mebuilder.entity.MeTask;
import org.junit.Test;

public class TaskBuilderTest {

  /**
   * MeTask classının id property si için test.
   */
  @Test
  public void testId() {
    MeTask newTask = GenericBuilder.of(MeTask::new)
        .with(MeTask::setId, 3L)
        .build();

    assertEquals(3L, newTask.getId().longValue());
  }

  /**
   * MeTask classının desc property si için test.
   */
  @Test
  public void testDesc() {
    MeTask newTask = GenericBuilder.of(MeTask::new)
        .with(MeTask::setDesc, "me desc")
        .build();

    assertEquals("me desc", newTask.getDesc());

  }

  /**
   * MeTask classının id property si için null testi.
   */
  @Test
  public void testIdNull() {
    MeTask newTask = GenericBuilder.of(MeTask::new)
        .with(MeTask::setDesc, "me desc")
        .build();

    assertNull(newTask.getId());
  }

  /**
   * MeTask classının desc property si için null testi.
   */
  @Test
  public void testDescNull() {
    MeTask newTask = GenericBuilder.of(MeTask::new)
        .with(MeTask::setId, 3L)
        .build();

    assertNull(newTask.getDesc());
  }

}