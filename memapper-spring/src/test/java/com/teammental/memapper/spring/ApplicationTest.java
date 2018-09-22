package com.teammental.memapper.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.teammental.memapper.Mapper;
import com.teammental.memapper.spring.to.TargetTo;
import com.teammental.memapper.spring.to.SourceTo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@Import({StartupMapConfigurationRegistry.class,
    StartupMapConfigurationRegistryBeanLoader.class,
    StartupDefaultMapperBeanConfiguration.class,
    TestConfig.class})
public class ApplicationTest {

  @Autowired
  private Mapper mapper;


  @Test
  public void mapperBeanShouldNotBeNull() {

    assertNotNull(mapper);
  }


  @Test
  public void shouldMapAsConfigured() {

    SourceTo source = new SourceTo();
    source.setId(894325);
    source.setName("43w5t43543");

    TargetTo target = mapper.map(source, TargetTo.class);

    assertEquals(source.getId(), target.getNo());
    assertEquals(source.getName(), target.getTitle());
  }
}
