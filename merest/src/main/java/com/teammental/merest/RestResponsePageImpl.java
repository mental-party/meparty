package com.teammental.merest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class RestResponsePageImpl<T> extends PageImpl<T> {

  private static final long serialVersionUID = 1L;
  private int size;
  private int totalPages;
  private int numberOfElements;
  private long totalElements;
  private List<T> content;
  private boolean first;
  private boolean last;

  @JsonIgnoreProperties(ignoreUnknown = true)
  public RestResponsePageImpl() {
    super(new ArrayList<T>());
  }

  public PageImpl<T> pageImpl() {
    return new PageImpl<T>(getContent(), new PageRequest(getNumber(),
        getSize(), getSort()), getTotalElements());
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  public int getNumberOfElements() {
    return numberOfElements;
  }

  public void setNumberOfElements(int numberOfElements) {
    this.numberOfElements = numberOfElements;
  }

  public long getTotalElements() {
    return totalElements;
  }

  public void setTotalElements(long totalElements) {
    this.totalElements = totalElements;
  }



  public List<T> getContent() {
    return content;
  }

  public void setContent(List<T> content) {
    this.content = content;
  }


  @Override
  public boolean isFirst() {
    return first;
  }

  public void setFirst(boolean first) {
    this.first = first;
  }

  @Override
  public boolean isLast() {
    return last;
  }

  public void setLast(boolean last) {
    this.last = last;
  }

  @JsonIgnore
  public void setSort(Sort sort) {
  }

  @JsonIgnore
  public void setPageable(Pageable pageable) {
  }

  @JsonIgnore
  public void setNumber(int number) {
  }
}
