package com.teammental.memapper;

import java.util.Optional;

/**
 * @author er-han on 30.03.2017
 *         This interface is used in MeMapper class to
 *         supply more readible code.
 */
public interface MapTo<TargetT> {

  /**
   * Maps the given source SourceT object
   * to a newly instantiated object of the given TargetType.
   *
   * @param targetType Target object's class.
   *                   Target type must have a public no-arg
   *                   constructor. Otherwise a TargetTypeInstantiationException
   *                   will be thrown.
   * @return Optional&lt;TargetT&gt;.
   */
  Optional<TargetT> mapTo(Class<TargetT> targetType);
}
