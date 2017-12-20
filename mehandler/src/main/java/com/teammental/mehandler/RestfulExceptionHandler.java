package com.teammental.mehandler;

import com.teammental.mecore.stereotype.dto.Dto;
import com.teammental.meexception.RestfulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(name = "enable-restfulexception-handler",
    prefix = "com.teammental.handler",
    havingValue = "true",
    matchIfMissing = true)
@RestControllerAdvice
public class RestfulExceptionHandler {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(RestfulExceptionHandler.class);

  /**
   * Handler method for RestfulException type.
   *
   * @param ex Auto-catched exception
   * @return HttpStatus=ex.statusCode
   */
  @ExceptionHandler(RestfulException.class)
  public ResponseEntity processHandler(RestfulException ex) {

    LOGGER.debug("RestfulException handler process: " + ex.getLocalizedMessage());

    HttpStatus status = HttpStatus.resolve(ex.getStatusCode());

    if (status == null) {
      status = HttpStatus.BAD_REQUEST;
    }

    Dto dto = ex.getDto();

    if (dto != null) {
      return ResponseEntity.status(status).body(dto);
    }

    return ResponseEntity.status(status).body(ex.getLocalizedMessage());
  }
}
