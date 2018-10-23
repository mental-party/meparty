package com.teammental.memapper;

import com.teammental.memapper.types.AbstractMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BeanMapperRegistry extends BeanMapper {

  private final List<AbstractMapper> customMappers;

  public BeanMapperRegistry() {

    this.customMappers = new ArrayList<>();
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

      T target = doCustomMap(source, mapper, null);

      if (target != null) {
        return target;
      }
    }

    return super.map(source, targetType);
  }

  @Override
  public <S, T> T map(S source, T target) {

    if (source == null || target == null) {
      return null;
    }

    Class<?> sourceType = source.getClass();
    Class<?> targetType = target.getClass();

    if (hasCustomMapper(sourceType, targetType)) {
      AbstractMapper mapper = getCustomMapper(sourceType, targetType);

      T targetResult = doCustomMap(source, mapper, target);

      if (targetResult != null) {

        return targetResult;
      }
    }

    return super.map(source, target);
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

    return super.map(sources, targetType);
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

  private <S, T> T doCustomMap(S source, AbstractMapper mapper, T target) {

    return (T) mapper.map(source, target);
  }

  private <S, T> Iterable<T> doCustomMap(Iterable<S> sources,
                                         AbstractMapper mapper) {

    List<T> targets = new ArrayList<>();

    for (S source
        : sources) {
      targets.add(doCustomMap(source, mapper, null));
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
