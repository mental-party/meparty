package com.teammental.memapper.abstractmapper;

import com.teammental.memapper.AbstractMapper;
import com.teammental.memapper.to.EnumGender;
import com.teammental.memapper.to.NameTo;
import com.teammental.memapper.to.abstractmapper.SourceTo;
import com.teammental.memapper.to.abstractmapper.TargetTo;

public class CustomAbstractMapper extends AbstractMapper<SourceTo, TargetTo> {

  public static final Integer NO = 54363;
  public static final String MALE_NAME = "John";
  public static final String MALE_SURNAME = "DOE";
  public static final String FEMALE_NAME = "Jane";
  public static final String FEMALE_SURNAME = "Doee";

  @Override
  protected boolean supports(Class<?> sourceClazz, Class<?> targetClazz) {

    return sourceClazz.isAssignableFrom(SourceTo.class)
        && targetClazz.isAssignableFrom(TargetTo.class);
  }

  @Override
  protected TargetTo doMap(SourceTo sourceTo) {

    TargetTo targetTo = new TargetTo();

    NameTo nameTo;
    if (sourceTo.getEnumGender()
        != null
        && sourceTo.getEnumGender().equals(EnumGender.MALE)) {

      nameTo = new NameTo(MALE_NAME, MALE_SURNAME);
    } else {
      nameTo = new NameTo(FEMALE_NAME, FEMALE_SURNAME);
    }
    targetTo.setNameTo(nameTo);

    targetTo.setNo(NO);

    return targetTo;
  }
}
