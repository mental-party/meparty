package com.teammental.memapper;

public abstract class AbstractMapper<SourceT, TargetT> {

  protected abstract boolean supports(Class<?> sourceClazz, Class<?> targetClazz);

  public TargetT map(SourceT sourceT) {

    return doMap(sourceT);
  }

  protected abstract TargetT doMap(SourceT sourceT);
}
