package com.teammental.memapper;

import com.teammental.memapper.core.MapWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class MeMapperToList<SourceT, TargetT> implements ToList<TargetT> {

  private Iterable<SourceT> sources;

  private static final Logger logger = LoggerFactory.getLogger(MeMapper.class);

  MeMapperToList(Iterable<SourceT> sources) {
    this.sources = sources;
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
  public Iterable<TargetT> to(Class<TargetT> targetType) {
    try {
      List<TargetT> targets = new ArrayList<>();
      for (SourceT source :
          sources) {
        TargetT target = newInstance(targetType);
        MapWorker<SourceT, TargetT> worker = new MapWorker<>(source, target);
        target = worker.map();
        targets.add(target);
      }
      return targets;

    } catch (Exception exception) {
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
   * @return Optional of Iterable of TargetT ;
   */
  @Override
  public Optional<Iterable<TargetT>> toOptional(Class<TargetT> targetType) {
    try {
      return Optional.ofNullable(to(targetType));
    } catch (Exception exception) {
      logger.debug(exception.getLocalizedMessage());
      return Optional.empty();
    }
  }

  private TargetT newInstance(Class<TargetT> targetType) throws Exception {
    TargetT target = targetType.newInstance();
    return target;
  }
}
