package com.teammental.meproxy.ui.editor.dto;

import java.util.List;

public class SelectItemResultDto {

  private List<SelectItemDto> selectItemDtos;
  private boolean hasMore;

  public List<SelectItemDto> getSelectItemDtos() {

    return selectItemDtos;
  }

  public void setSelectItemDtos(
      List<SelectItemDto> selectItemDtos) {

    this.selectItemDtos = selectItemDtos;
  }

  public boolean isHasMore() {

    return hasMore;
  }

  public void setHasMore(boolean hasMore) {

    this.hasMore = hasMore;
  }
}
