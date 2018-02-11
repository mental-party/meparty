package com.teammental.merest.autoconfiguration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teammental.medto.FilterDto;
import com.teammental.merest.FilterDtoWrapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

@Component
public final class FilterDtoConverter extends AbstractHttpMessageConverter<FilterDto> {

  private static final Logger LOGGER = LoggerFactory.getLogger(FilterDtoConverter.class);

  public FilterDtoConverter() {
    super(MediaType.APPLICATION_JSON_UTF8);
  }


  @Override
  protected boolean supports(Class<?> clazz) {

    return FilterDto.class.isAssignableFrom(clazz);
  }

  @Override
  protected FilterDto readInternal(Class<? extends FilterDto> clazz, HttpInputMessage inputMessage)
      throws IOException, HttpMessageNotReadableException {

    InputStream istream = inputMessage.getBody();
    String source = toString(istream);

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    try {
      FilterDtoWrapper filterDtoWrapper =
          objectMapper.readValue(source, FilterDtoWrapper.class);
      JavaType actualWrapperType = objectMapper.getTypeFactory().constructParametricType(FilterDtoWrapper.class,
          filterDtoWrapper.getFilterDtoType());

      FilterDtoWrapper wrapper = objectMapper.readValue(source, actualWrapperType);

      Class<?> type = wrapper.getFilterDtoType();

      return (FilterDto) type.cast(wrapper.getFilterDto());
    } catch (IOException ex) {
      LOGGER.error(ex.getLocalizedMessage());
      return null;
    }

  }

  @Override
  protected void writeInternal(FilterDto filterDto, HttpOutputMessage outputMessage)
      throws IOException, HttpMessageNotWritableException {

    FilterDtoWrapper wrapper = new FilterDtoWrapper();
    wrapper.setFilterDto(filterDto);
    wrapper.setFilterDtoType(filterDto.getClass());

    ObjectMapper objectMapper = new ObjectMapper();
    byte[] body = objectMapper.writeValueAsBytes(wrapper);

    OutputStream outputStream = outputMessage.getBody();
    outputStream.write(body);
    outputStream.close();
  }

  private static String toString(InputStream inputStream) {
    Scanner scanner = new Scanner(inputStream, "UTF-8");
    return scanner.next();
  }
}
