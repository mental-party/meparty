package com.teammental.memapper.spring;

import com.teammental.memapper.BeanMapper;
import com.teammental.memapper.Mapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupDefaultMapperBeanConfiguration {

  /**
   * Default {@link Mapper} bean.
   *
   * @return {@link BeanMapper} instance.
   */
  @Bean
  @ConditionalOnMissingBean(Mapper.class)
  public Mapper mapper() {

    return new BeanMapper();
  }
}
