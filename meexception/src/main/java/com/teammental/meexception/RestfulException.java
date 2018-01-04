package com.teammental.meexception;

import com.teammental.mecore.stereotype.dto.Dto;
import com.teammental.mecore.stereotype.exception.Exception;

public class RestfulException extends RuntimeException implements Exception {

  private int statusCode;

  private Dto dto;

  protected RestfulException(int statusCode, Dto dto, String message, Throwable cause) {
    super(message, cause);
    this.statusCode = statusCode;
    this.dto = dto;
  }

  public int getStatusCode() {

    return statusCode;
  }

  public Dto getDto() {
    return dto;
  }

  public static Builder getBuilder() {
    return new Builder();
  }

  public static class Builder {

    private int statusCode;
    private Dto dto;
    private Throwable cause;
    private String message;

    Builder() {
      statusCode = 500;
      dto = null;
      cause = null;
      message = null;
    }

    public Builder statusCode(int statusCode) {
      this.statusCode = statusCode;
      return this;
    }

    public Builder dto(Dto dto) {
      this.dto = dto;
      return this;
    }

    public Builder cause(Throwable cause) {
      this.cause = cause;
      return this;
    }

    public Builder message(String message) {
      this.message = message;
      return this;
    }

    public RestfulException build() {
      return new RestfulException(statusCode, dto, message, cause);
    }
  }
}
