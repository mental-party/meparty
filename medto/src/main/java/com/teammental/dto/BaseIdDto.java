package com.teammental.dto;

import java.io.Serializable;

public abstract class BaseIdDto<IdT extends Serializable> implements IdDto<IdT> {

  protected IdT id;

  @Override
  public IdT getId() {
    return id;
  }

  @Override
  public void setId(IdT id) {
    this.id = id;
  }
}
