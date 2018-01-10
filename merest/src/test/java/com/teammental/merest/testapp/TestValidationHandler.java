package com.teammental.merest.testapp;

import com.teammental.mevalidation.dto.FieldErrorDto;
import com.teammental.mevalidation.dto.ValidationResultDto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TestValidationHandler {

  /**
   * Rest handler for validation errors.
   * @param ex handled exception
   * @return rest result
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<?> processHandler(MethodArgumentNotValidException ex) {

    BindingResult bindingResult = ex.getBindingResult();
    List<FieldError> fieldErrors = bindingResult.getFieldErrors();
    List<FieldErrorDto> fieldErrorDtos = fieldErrors.stream()
        .map(FieldErrorDto::new)
        .collect(Collectors.toList());

    ValidationResultDto validationResultDto = new ValidationResultDto();
    validationResultDto.setFieldErrors(fieldErrorDtos);

    return ResponseEntity.badRequest().body(validationResultDto);
  }
}
