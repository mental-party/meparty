package com.teammental.memapper;

import com.teammental.memapper.types.Mapper;
import java.util.ArrayList;
import java.util.List;

public class BeanMapper implements Mapper {

  /**
   * {@inheritDoc}
   */
  @Override
  public <S, T> T map(S source, Class<T> targetType) {

    MeMapperTo<S, T> mapperTo = new MeMapperTo<>(source);
    return mapperTo.to(targetType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S, T> Iterable<T> map(Iterable<S> sources, Class<T> targetType) {

    MeMapperToList<S, T> mapperToList = new MeMapperToList<>(sources);
    Iterable<T> targets = mapperToList.to(targetType);

    if (targets == null) {
      targets = new ArrayList<>();
    }

    return targets;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S, T> List<T> map(List<S> sources, Class<T> targetType) {

    return (List<T>) map((Iterable<S>) sources, targetType);
  }
}
