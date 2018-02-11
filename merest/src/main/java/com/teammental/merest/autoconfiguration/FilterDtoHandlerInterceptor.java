package com.teammental.merest.autoconfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teammental.medto.FilterDto;
import com.teammental.merest.FilterDtoWrapper;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class FilterDtoHandlerInterceptor extends HandlerInterceptorAdapter {

  private static final Logger LOGGER = LoggerFactory.getLogger(FilterDtoWrapper.class);

  @Override
  public boolean preHandle (HttpServletRequest request,
                            HttpServletResponse response,
                            Object handler) throws Exception {

    try {
      HandlerMethod method = (HandlerMethod)handler;
      MethodParameter[] parameters = method.getMethodParameters();
      if (Arrays.stream(parameters)
          .anyMatch(methodParameter ->
              methodParameter.hasParameterAnnotation(RequestAttribute.class)
          && methodParameter.getParameterType().equals(FilterDto.class))) {

        FilterDtoRequestWraper requestWraper = new FilterDtoRequestWraper(request);
        ObjectMapper objectMapper = new ObjectMapper();
        String body = requestWraper.getBody();
        FilterDtoWrapper filterDtoWrapper
             = objectMapper.readValue(body, FilterDtoWrapper.class);

        Object requestAttribute = filterDtoWrapper.getFilterDto();

        RequestAttribute requestAttributeAnnotation = Arrays.stream(parameters)
            .filter(methodParameter ->
                methodParameter.hasParameterAnnotation(RequestAttribute.class)
                    && methodParameter.getParameterType().equals(FilterDto.class))
            .findFirst().get().getParameterAnnotation(RequestAttribute.class);

        request.setAttribute(requestAttributeAnnotation.name(), requestAttribute);

      }
    } catch (ClassCastException ex) {
      LOGGER.error(ex.getLocalizedMessage());
    } catch (Exception ex) {
      LOGGER.error(ex.getLocalizedMessage());
    }
    return true;
  }
}
