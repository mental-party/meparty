package com.teammental.memapper.types;

import com.teammental.mehelper.AssertHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractMapper<SourceT, TargetT> {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(AbstractMapper.class);

  private Class<?> targetClazz = null;

  /**
   * Checks if this mapper supports given source and target types.
   *
   * @param sourceClazz source type
   * @param targetClazz target type
   * @return supports
   */
  public boolean supports(Class<?> sourceClazz, Class<?> targetClazz) {
    AssertHelper.notNull(sourceClazz, targetClazz);
    this.targetClazz = targetClazz;

    return checkSupports(sourceClazz, targetClazz);
  }

  protected abstract boolean checkSupports(Class<?> sourceClazz, Class<?> targetClazz);


  /**
   * Map source object to given type.
   *
   * @param source source
   * @return target instance
   */
  public TargetT map(SourceT source) {

    try {
      Object target = targetClazz.newInstance();
      return doMap(source, (TargetT) target);

    } catch (InstantiationException | IllegalAccessException ex) {
      LOGGER.warn(ex.getLocalizedMessage());
    }

    return null;
  }


  /**
   * Map source object to target object.
   *
   * @param source source
   * @return target
   */
  public TargetT map(SourceT source, TargetT target) {

    if (target == null) {
      return map(source);
    }

    return doMap(source, target);
  }

  protected abstract TargetT doMap(SourceT source, TargetT target);

}
