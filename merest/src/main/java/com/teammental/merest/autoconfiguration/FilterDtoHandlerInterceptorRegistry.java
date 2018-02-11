package com.teammental.merest.autoconfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class FilterDtoHandlerInterceptorRegistry implements WebMvcConfigurer {

  @Override
  public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
    registry.addInterceptor(new FilterDtoHandlerInterceptor());
  }
}
