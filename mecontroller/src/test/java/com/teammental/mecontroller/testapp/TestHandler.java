package com.teammental.mecontroller.testapp;

import com.teammental.meexception.dto.DtoCrudException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TestHandler {

  /**
   * Handler method for test app.
   * @param ex Auto-catched exception
   * @return HttpStatus=INTERNAL_SERVER_ERROR
   */
  @ExceptionHandler(DtoCrudException.class)
  public ResponseEntity processHandler(Exception ex) {

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
  }
}
