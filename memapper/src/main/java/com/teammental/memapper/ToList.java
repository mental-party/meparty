package com.teammental.memapper;

import java.util.Optional;

public interface ToList<TargetT> {
  /**
   * Maps the given source SourceT object
   * to a newly instantiated object of the given TargetType.
   *
   * @param targetType Target object's class.
   *                   Target type must have a public no-arg
   *                   constructor. Otherwise a TargetTypeInstantiationException
   *                   will be thrown and return value will be null.
   * @return Optional Iterable TargetT ;
   */
  Iterable<TargetT> to(Class<TargetT> targetType);

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
  Optional<Iterable<TargetT>> toOptional(Class<TargetT> targetType);
}
