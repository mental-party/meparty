package com.teammental.memapper;

import com.teammental.memapper.types.Mapper;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BeanMapperRegistry implements Mapper {

  private List<AbstractMapper> customMappers;

  public BeanMapperRegistry() {

    customMappers = new ArrayList<>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S, T> T map(S source, Class<T> targetType) {

    if (source == null || targetType == null) {
      return null;
    }

    Class<?> sourceType = source.getClass();

    if (hasCustomMapper(sourceType, targetType)) {
      AbstractMapper mapper = getCustomMapper(sourceType, targetType);

      T target = doCustomMap(source, mapper);

      if (target != null) {
        return target;
      }
    }

    return (T) MeMapper.from(source).to(targetType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S, T> Iterable<T> map(Iterable<S> sources, Class<T> targetType) {

    if (sources == null
        || !sources.iterator().hasNext()
        || targetType == null) {
      return new ArrayList<>();
    }

    Class<?> sourceType = StreamSupport
        .stream(sources.spliterator(), false)
        .collect(Collectors.toList())
        .get(0).getClass();

    if (hasCustomMapper(sourceType, targetType)) {

      AbstractMapper mapper = getCustomMapper(sourceType, targetType);

      List<T> targets = (List<T>) doCustomMap(sources, mapper);

      if (targets != null
          && !targets.isEmpty()) {
        return targets;
      }
    }


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

    return (List<T>) map((Iterable<? extends Object>) sources, targetType);
  }

  /**
   * Register custom {@link AbstractMapper}.
   *
   * @param mapper mapper
   */
  public void register(AbstractMapper mapper) {

    customMappers.add(mapper);
  }

  private <S, T> T doCustomMap(S source, AbstractMapper mapper) {

    return (T) mapper.doMap(source);
  }

  private <S, T> Iterable<T> doCustomMap(Iterable<S> sources,
                                         AbstractMapper mapper) {

    List<T> targets = new ArrayList<>();

    for (S source
        : sources) {
      targets.add(doCustomMap(source, mapper));
    }

    return targets;
  }

  private AbstractMapper getCustomMapper(Class<?> sourceClazz, Class<?> targetClazz) {

    return customMappers
        .stream()
        .filter(mapper -> mapper.supports(sourceClazz, targetClazz))
        .findFirst()
        .get();
  }


  private boolean hasCustomMapper(Class<?> sourceClazz, Class<?> targetClazz) {

    return customMappers.stream()
        .anyMatch(mapper -> mapper.supports(sourceClazz, targetClazz));
  }
}
