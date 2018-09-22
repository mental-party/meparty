package com.teammental.memapper;

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

    return (Iterable<T>) MeMapper.from(sources).to(targetType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S, T> List<T> map(List<S> sources, Class<T> targetType) {

    return (List<T>) MeMapper.from(sources).to(targetType);
  }
}
