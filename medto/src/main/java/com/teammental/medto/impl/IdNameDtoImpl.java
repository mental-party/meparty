package com.teammental.medto.impl;

import com.teammental.medto.AbstractIdNameDto;

public class IdNameDtoImpl extends AbstractIdNameDto<Integer> {

  public IdNameDtoImpl() {
  }

  private IdNameDtoImpl(Integer id, String name) {
    setId(id);
    setName(name);
  }

  public static Builder getBuilder() {

    return new Builder();
  }

  public static class Builder {

    private Integer id;
    private String name;

    Builder() {

    }

    /**
     * Set id in builder.
     *
     * @param id id
     * @return Builder class
     */
    public Builder id(int id) {

      this.id = id;
      return this;
    }

    /**
     * Set name in builder.
     *
     * @param name name
     * @return Builder class
     */
    public Builder name(String name) {

      this.name = name;
      return this;
    }

    public IdNameDtoImpl build() {

      return new IdNameDtoImpl(id, name);
    }
  }
}
