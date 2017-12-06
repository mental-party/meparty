package com.teammental.mebuilder.entity;

/**
 * Örnek basit bir class.
 * Created by coskun on 12.06.2017.
 */
public class MeTask {
  private Long id;
  private String desc;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
