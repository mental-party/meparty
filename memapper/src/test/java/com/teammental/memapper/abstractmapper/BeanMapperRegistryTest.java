package com.teammental.memapper.abstractmapper;

import com.teammental.memapper.BeanMapperRegistry;
import com.teammental.memapper.to.EnumGender;
import com.teammental.memapper.to.abstractmapper.SourceTo;
import com.teammental.memapper.to.abstractmapper.TargetTo;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

public class BeanMapperRegistryTest {

  @Test
  public void shouldUseCustomMapper_whenHasCustomMapper() {

    BeanMapperRegistry mapper = new BeanMapperRegistry();
    mapper.register(new CustomAbstractMapper());

    final String no = "541gre";
    final EnumGender enumGender = EnumGender.MALE;
    SourceTo sourceTo = new SourceTo();
    sourceTo.setEnumGender(enumGender);
    sourceTo.setNo(no);

    TargetTo targetTo = mapper.map(sourceTo, TargetTo.class);

    assertNotNull(targetTo);
    assertEquals(CustomAbstractMapper.MALE_NAME, targetTo.getNameTo().getFirstName());
    assertEquals(CustomAbstractMapper.MALE_SURNAME, targetTo.getNameTo().getLastName());
    assertEquals(CustomAbstractMapper.NO, targetTo.getNo());
  }

  @Test
  public void shouldUseCustomMapper_test2_whenHasCustomMapper() {

    BeanMapperRegistry mapper = new BeanMapperRegistry();
    mapper.register(new CustomAbstractMapper());

    final String no = "5y6t5466546";
    final String title = "titleTitleTitle";
    final EnumGender enumGender = EnumGender.FEMALE;
    SourceTo sourceTo = new SourceTo();
    sourceTo.setEnumGender(enumGender);
    sourceTo.setTitle(title);
    sourceTo.setNo(no);

    TargetTo targetTo = mapper.map(sourceTo, TargetTo.class);

    assertNotNull(targetTo);
    assertEquals(CustomAbstractMapper.FEMALE_NAME, targetTo.getNameTo().getFirstName());
    assertEquals(CustomAbstractMapper.FEMALE_SURNAME, targetTo.getNameTo().getLastName());
    assertEquals(CustomAbstractMapper.NO, targetTo.getNo());

    assertNotEquals(title, targetTo.getTitle());
  }

  @Test
  public void shouldUseDefaulMapper_whenHasNotCustomMapper() {

    BeanMapperRegistry mapper = new BeanMapperRegistry();

    final String no = "5y6t5466546";
    final String title = "titleTitleTitle";
    final EnumGender enumGender = EnumGender.FEMALE;
    SourceTo sourceTo = new SourceTo();
    sourceTo.setEnumGender(enumGender);
    sourceTo.setNo(no);
    sourceTo.setTitle(title);

    TargetTo targetTo = mapper.map(sourceTo, TargetTo.class);

    assertNotNull(targetTo);
    assertNull(targetTo.getNameTo());
    assertNotEquals(CustomAbstractMapper.NO, targetTo.getNo());

    assertEquals(title, targetTo.getTitle());
  }
}
