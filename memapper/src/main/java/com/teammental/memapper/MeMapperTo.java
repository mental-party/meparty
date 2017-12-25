package com.teammental.memapper;

import com.teammental.memapper.core.MapWorker;
import com.teammental.memapper.exception.TargetTypeInstantiationException;
import com.teammental.memapper.util.mapping.MapByFieldNameUtil;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class MeMapperTo<SourceT, TargetT> implements To<TargetT> {

  private SourceT source;

  private static final Logger logger = LoggerFactory.getLogger(MeMapper.class);

  MeMapperTo(SourceT source) {

    this.source = source;
  }

  /**
   * Maps the given source SourceT object
   * to a newly instantiated object of the given TargetType.
   *
   * @param targetType Target object's class. Target type must have a public no-arg constructor.
   *                   Otherwise a TargetTypeInstantiationException will be thrown and return value
   *                   will be null.
   * @return TargetT.
   */
  @Override
  public TargetT to(Class<TargetT> targetType) {

    try {
      TargetT target = targetType.newInstance();
      return to(target);

    } catch (Exception ex) {
      TargetTypeInstantiationException exception =
          new TargetTypeInstantiationException(targetType, ex);
      logger.debug(exception.getLocalizedMessage());
      return null;
    }
  }

  /**
   * Maps the given source SourceT object
   * to target object.
   *
   * @param target Target object.
   * @return Optional of TargetT.
   */
  @Override
  public TargetT to(TargetT target) {

    if (target == null) {
      return null;
    }
    MapWorker<SourceT, TargetT> mapWorker = new MapWorker<>(source, target);
    return mapWorker.map();
  }

  /**
   * Maps the given source SourceT object
   * to a newly instantiated object of the given TargetType.
   *
   * @param targetType Target object's class. Target type must have a public no-arg constructor.
   *                   Otherwise a TargetTypeInstantiationException will be thrown and return value
   *                   will be null.
   * @return Optional of TargetT.
   */
  @Override
  public Optional<TargetT> toOptional(Class<TargetT> targetType) {
    return Optional.ofNullable(to(targetType));
  }

  /**
   * Maps the given source SourceT object
   * to target object.
   *
   * @param target Target object.
   * @return Optional of TargetT.
   */
  @Override
  public Optional<TargetT> toOptional(TargetT target) {

    return Optional.ofNullable(to(target));
  }
}
