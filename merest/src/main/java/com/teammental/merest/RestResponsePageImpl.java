package com.teammental.merest;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class RestResponsePageImpl<T> extends PageImpl<T> {

  private int number;
  private int size;
  private int totalPages;
  private int numberOfElements;
  private long totalElements;
  private boolean previousPage;
  private boolean first;
  private boolean nextPage;
  private boolean last;
  private List<T> content;

  public RestResponsePageImpl(List<T> content, Pageable pageable, long total) {
    super(content, pageable, total);
  }

  public RestResponsePageImpl(List<T> content) {
    super(content);
  }

  public RestResponsePageImpl() {
    super(new ArrayList());
  }

  public PageImpl<T> pageImpl() {
    return new PageImpl<T>(getContent(), new PageRequest(getNumber(),
        getSize(), getSort()), getTotalElements());
  }

  @Override
  public int getNumber() {

    return number;
  }

  public void setNumber(int number) {

    this.number = number;
  }

  @Override
  public int getSize() {

    return size;
  }

  public void setSize(int size) {

    this.size = size;
  }

  @Override
  public int getTotalPages() {

    return totalPages;
  }

  public void setTotalPages(int totalPages) {

    this.totalPages = totalPages;
  }

  @Override
  public int getNumberOfElements() {

    return numberOfElements;
  }

  public void setNumberOfElements(int numberOfElements) {

    this.numberOfElements = numberOfElements;
  }

  @Override
  public long getTotalElements() {

    return totalElements;
  }

  public void setTotalElements(long totalElements) {

    this.totalElements = totalElements;
  }

  public boolean isPreviousPage() {

    return previousPage;
  }

  public void setPreviousPage(boolean previousPage) {

    this.previousPage = previousPage;
  }

  @Override
  public boolean isFirst() {

    return first;
  }

  public void setFirst(boolean first) {

    this.first = first;
  }

  public boolean isNextPage() {

    return nextPage;
  }

  public void setNextPage(boolean nextPage) {

    this.nextPage = nextPage;
  }

  @Override
  public boolean isLast() {

    return last;
  }

  public void setLast(boolean last) {

    this.last = last;
  }

  @Override
  public List<T> getContent() {

    return content;
  }

  public void setContent(List<T> content) {

    this.content = content;
  }

}
