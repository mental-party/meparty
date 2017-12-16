package com.teammental.memapper;

import java.util.Optional;

public interface To<TargetT> {
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
  TargetT to(Class<TargetT> targetType);

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
  Optional<TargetT> toOptional(Class<TargetT> targetType);

  /**
   * Maps the given source SourceT object
   * to target object.
   *
   * @param target Target object.
   * @return Optional of TargetT.
   */
  TargetT to(TargetT target);

  /**
   * Maps the given source SourceT object
   * to target object.
   *
   * @param target Target object.
   * @return Optional of TargetT.
   */
  Optional<TargetT> toOptional(TargetT target);
}
