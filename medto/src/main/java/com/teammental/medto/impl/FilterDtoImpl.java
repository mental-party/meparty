package com.teammental.medto.impl;

import com.teammental.medto.FilterDto;
import com.teammental.medto.page.PageRequestDto;

public class FilterDtoImpl implements FilterDto {

  private PageRequestDto page;

  public void setPage(PageRequestDto page) {
    this.page = page;
  }

  @Override
  public PageRequestDto getPage() {
    return page;
  }
}