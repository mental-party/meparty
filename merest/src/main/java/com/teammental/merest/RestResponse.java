package com.teammental.merest;

import com.teammental.mecore.stereotype.dto.Dto;
import com.teammental.mevalidation.dto.ValidationResultDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class RestResponse<T> extends ResponseEntity<T> {

  private String responseMessage;
  private ValidationResultDto validationResult;
  private Dto responseDto;

  public ValidationResultDto getValidationResult() {
    return validationResult;
  }

  void setValidationResult(ValidationResultDto validationResult) {
    this.validationResult = validationResult;
  }

  public boolean hasValidationResult() {
    return validationResult != null;
  }

  public String getResponseMessage() {
    return responseMessage;
  }

  public void setResponseMessage(String responseMessage) {
    this.responseMessage = responseMessage;
  }

  public Dto getResponseDto() {
    return responseDto;
  }

  void setResponseDto(Dto responseDto) {
    this.responseDto = responseDto;
  }

  // region constructors

  public RestResponse(HttpStatus status) {
    super(status);
  }

  public RestResponse(T body, HttpStatus status) {
    super(body, status);
  }

  public RestResponse(MultiValueMap<String, String> headers, HttpStatus status) {
    super(headers, status);
  }

  public RestResponse(T body, MultiValueMap<String, String> headers, HttpStatus status) {
    super(body, headers, status);
  }

  public RestResponse(ResponseEntity<T> responseEntity) {
    super(responseEntity.getBody(), responseEntity.getHeaders(), responseEntity.getStatusCode());
  }

  // endregion constructors
}
