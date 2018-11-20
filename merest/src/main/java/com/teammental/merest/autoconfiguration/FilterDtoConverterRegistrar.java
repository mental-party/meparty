package com.teammental.merest.autoconfiguration;

import java.util.List;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AutoConfigureOrder()
public class FilterDtoConverterRegistrar implements WebMvcConfigurer {


  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    Object[] tempArray = converters.toArray();
    converters.clear();
    converters.add(new FilterDtoConverter());
    for (Object converter :
        tempArray) {
      converters.add((HttpMessageConverter<?>) converter);
    }
  }
}
