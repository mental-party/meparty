package com.teammental.medto;

import java.io.Serializable;

public abstract class AbstractIdDto<IdT extends Serializable> implements IdDto<IdT> {

  private IdT id;

  @Override
  public IdT getId() {
    return id;
  }

  @Override
  public void setId(IdT id) {
    this.id = id;
  }
}
