package com.teammental.merest;

/**
 * Created by sa on 2/11/2018.
 */
public class FilterDtoWrapper<T> {

  private T filterDto;
  private Class<T> filterDtoType;

  public T getFilterDto() {

    return filterDto;
  }

  public void setFilterDto(T filterDto) {

    this.filterDto = filterDto;
  }

  public Class<T> getFilterDtoType() {

    return filterDtoType;
  }

  public void setFilterDtoType(Class<T> filterDtoType) {

    this.filterDtoType = filterDtoType;
  }
}
