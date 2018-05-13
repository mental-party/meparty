package com.teammental.memapper;

public class BeanMapper {


  /**
   * Maps the given source of S object
   * to a newly instantiated object of the given targetType.
   *
   * @param source source object
   * @param targetType Target object's class.
   *                   Target type must have a public no-arg
   *                   constructor. Otherwise a TargetTypeInstantiationException
   *                   will be thrown and return value will be null.
   * @return Optional Iterable TargetT ;
   */
  public <S, T> T map(S source, Class<T> targetType) {

    return (T) MeMapper.from(source).to(targetType);
  }

  /**
   * Maps the given sources of S object
   * to a newly instantiated objects of the given targetType.
   *
   * @param sources source objects
   * @param targetType Target object's class.
   *                   Target type must have a public no-arg
   *                   constructor. Otherwise a TargetTypeInstantiationException
   *                   will be thrown and return value will be null.
   * @return Optional Iterable TargetT ;
   */
  public <S, T> Iterable<T> map(Iterable<S> sources, Class<T> targetType) {

    return (Iterable<T>) MeMapper.from(sources).to(targetType);
  }
}
