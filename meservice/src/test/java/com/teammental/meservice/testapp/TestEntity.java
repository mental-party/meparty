package com.teammental.meservice.testapp;

import java.util.Random;

import com.teammental.meentity.BaseEntity;
import com.teammental.mehelper.StringHelper;

public class TestEntity extends BaseEntity {
  private Integer id;
  private String name;

  public Integer getId() {

    return id;
  }

  public void setId(Integer id) {

    this.id = id;
  }

  public String getName() {

    return name;
  }

  public void setName(String name) {

    this.name = name;
  }

  /**
   * Generates a random TestEntity object.
   * @return testEntity
   */
  public static TestEntity buildRandom() {
    Random random = new Random();
    TestEntity testEntity = new TestEntity();
    testEntity.setId(random.nextInt());
    testEntity.setName(StringHelper.generateRandomString(10));
    return testEntity;
  }
}
