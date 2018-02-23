package com.teammental.merest;

import com.teammental.mevalidation.dto.ValidationResultDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class RestResponse<T> extends ResponseEntity<T> {

  // region fields
  private String responseMessage;
  private ValidationResultDto validationResult;
  //endregion fields

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

  // region getters & setters

  public ValidationResultDto getValidationResult() {

    return validationResult;
  }

  void setValidationResult(ValidationResultDto validationResult) {

    this.validationResult = validationResult;
  }

  /**
   * Checks if there is any validation result.
   * @return boolean
   */
  public boolean hasValidationResult() {

    return validationResult != null
        && (validationResult.hasFieldError()
        || validationResult.hasGlobalError());
  }

  public String getResponseMessage() {

    return responseMessage;
  }

  public void setResponseMessage(String responseMessage) {

    this.responseMessage = responseMessage;
  }

  // endregion getters & setters

  /**
   * Checks status.
   *
   * @return rest operation result
   */
  public boolean isSuccess() {

    switch (getStatusCode()) {
      case OK:
      case CREATED:
      case NO_CONTENT:
        return true;
      default:
        return false;
    }
  }

  public static <T> RestResponse<T> of(ResponseEntity<T> responseEntity) {

    return new RestResponse<>(responseEntity);
  }

}
