package com.teammental.medto;

import java.io.Serializable;

public abstract class AbstractIdNameDto<IdT extends Serializable>
    extends AbstractIdDto<IdT>
    implements IdNameDto<IdT> {

  private String name;

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }
}
