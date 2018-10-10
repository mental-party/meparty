package com.teammental.medto.page;

import com.teammental.mecore.stereotype.dto.Dto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageRequestDto implements Dto {

  private int page;
  private int size;
  private List<SortDto> sortDtos;

  /**
   * No-arg constructor to be used when deserializing.
   */
  public PageRequestDto() {
    this.page = 1;
    this.size = 10;
    sortDtos = new ArrayList<>();
  }

  /**
   * Constructor for setting page and size.
   *
   * @param page requested page number.
   * @param size requeseted page size
   */
  public PageRequestDto(int page, int size) {

    this.page = page;
    this.size = size;
    this.sortDtos = new ArrayList<>();
  }

  /**
   * Constructor for setting page, size and sortings.
   *
   * @param page     requested page number.
   * @param size     requeseted page size
   * @param sortDtos sorting params.
   */
  public PageRequestDto(int page, int size, SortDto... sortDtos) {

    this.page = page;
    this.size = size;
    this.sortDtos = new ArrayList<>();
    for (SortDto sortDto :
        sortDtos) {
      this.sortDtos.add(sortDto);
    }
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

  public List<SortDto> getSortDtos() {
    return sortDtos;
  }

  public void setSortDtos(List<SortDto> sortDtos) {
    this.sortDtos = sortDtos;
  }

  public static class SortDto {
    private String property;
    private boolean sortAsc;

    public SortDto() {
    }

    public SortDto(String property, boolean sortAsc) {
      this.property = property;
      this.sortAsc = sortAsc;
    }

    public String getProperty() {
      return property;
    }

    public void setProperty(String property) {
      this.property = property;
    }

    public boolean isSortAsc() {
      return sortAsc;
    }

    public void setSortAsc(boolean sortAsc) {
      this.sortAsc = sortAsc;
    }

    /**
     * Convert to {@link Sort}.
     *
     * @return sort
     */
    public Sort toSort() {
      Sort.Direction direction = sortAsc ? Sort.Direction.ASC
          : Sort.Direction.DESC;

      Sort sort = Sort.by(direction, property);
      return sort;
    }
  }

  /**
   * Convert to {@link PageRequest}.
   *
   * @return pageRequest object
   */
  public PageRequest toPageRequest() {

    PageRequest pageRequest;
    if (sortDtos.isEmpty()) {
      pageRequest = PageRequest.of(page - 1, size);
    } else {
      Sort firstSort = sortDtos.get(0).toSort();

      for (int i = 1; i < sortDtos.size(); i++) {
        Sort nextSort = sortDtos.get(i).toSort();
        firstSort = firstSort.and(nextSort);
      }

      pageRequest = PageRequest.of(page - 1, size, firstSort);
    }

    return pageRequest;
  }


}
