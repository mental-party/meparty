package com.teammental.medto.impl;

import com.teammental.medto.FilterDto;
import org.springframework.data.domain.Pageable;

public class FilterDtoImpl implements FilterDto {

  private Pageable page;

  public void setPage(Pageable page) {
    this.page = page;
  }

  @Override
  public Pageable getPage() {
    return page;
  }
}