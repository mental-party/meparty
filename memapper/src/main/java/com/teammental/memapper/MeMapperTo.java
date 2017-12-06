package com.teammental.memapper;

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
   * @param targetType Target object's class.
   *                   Target type must have a public no-arg
   *                   constructor. Otherwise a TargetTypeInstantiationException
   *                   will be thrown and return value will be null.
   * @return TargetT.
   */
  @Override
  public TargetT to(Class<TargetT> targetType) {
    try {
      return MapByFieldNameUtil.map(source, targetType);
    } catch (TargetTypeInstantiationException exception) {
      logger.debug(exception.getLocalizedMessage());
      return null;
    }
  }

  /**
   * Maps the given source SourceT object
   * to a newly instantiated object of the given TargetType.
   *
   * @param targetType Target object's class.
   *                   Target type must have a public no-arg
   *                   constructor. Otherwise a TargetTypeInstantiationException
   *                   will be thrown and return value will be null.
   * @return Optional of TargetT.
   */
  @Override
  public Optional<TargetT> toOptional(Class<TargetT> targetType) {
    try {
      return Optional.ofNullable(MapByFieldNameUtil.map(source, targetType));
    } catch (Exception exception) {
      logger.debug(exception.getLocalizedMessage());
      return Optional.empty();
    }
  }
}
