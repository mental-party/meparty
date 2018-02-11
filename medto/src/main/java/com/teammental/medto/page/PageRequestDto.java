package com.teammental.medto.page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageRequestDto extends PageRequest {

  private int page;
  private int size;
  private Sort sort;
  private int pageSize;
  private int pageNumber;
  private long offset;

  public PageRequestDto() {
    super(1,10);
    this.page = 1;
    this.size = 10;
  }

  public PageRequestDto(int page, int size) {

    super(page, size);
    this.page = page;
    this.size = size;
  }

  public PageRequestDto(int page, int size, Sort.Direction direction,
                        String... properties) {

    super(page, size, direction, properties);
    this.page = page;
    this.size = size;
  }

  public PageRequestDto(int page, int size, Sort sort) {

    super(page, size, sort);
    this.page = page;
    this.size = size;
    this.sort = sort;
  }

  public int getPage() {

    return page;
  }

  public void setPage(int page) {

    this.page = page;
  }

  public int getSize() {

    return size;
  }

  public void setSize(int size) {

    this.size = size;
  }

  @Override
  public Sort getSort() {

    return sort;
  }

  public void setSort(Sort sort) {

    this.sort = sort;
  }

  @Override
  public int getPageSize() {

    return pageSize;
  }

  public void setPageSize(int pageSize) {

    this.pageSize = pageSize;
  }

  @Override
  public int getPageNumber() {

    return pageNumber;
  }

  public void setPageNumber(int pageNumber) {

    this.pageNumber = pageNumber;
  }

  @Override
  public long getOffset() {

    return offset;
  }

  public void setOffset(long offset) {

    this.offset = offset;
  }
}
