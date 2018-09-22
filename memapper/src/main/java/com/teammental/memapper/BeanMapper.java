package com.teammental.memapper;

import java.util.ArrayList;
import java.util.List;

public class BeanMapper implements Mapper {

  /**
   * {@inheritDoc}
   */
  @Override
  public <S, T> T map(S source, Class<T> targetType) {

    return (T) MeMapper.from(source).to(targetType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S, T> Iterable<T> map(Iterable<S> sources, Class<T> targetType) {

    Iterable<T> mapped = (Iterable<T>) MeMapper.from(sources).to(targetType);
    if (mapped == null) {
      mapped = new ArrayList<>();
    }

    return mapped;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S, T> List<T> map(List<S> sources, Class<T> targetType) {

    List<T> mapped = (List<T>) MeMapper.from(sources).to(targetType);
    if (mapped == null) {
      mapped = new ArrayList<>();
    }

    return mapped;
  }
}
