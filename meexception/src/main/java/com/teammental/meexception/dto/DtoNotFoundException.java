package com.teammental.meexception.dto;

public class DtoNotFoundException extends DtoCrudException {
  private static final int statusCode = 404;

  public DtoNotFoundException() {
    super(null, statusCode);
  }

  public DtoNotFoundException(String message) {
    super(null, statusCode, message);
  }

  public DtoNotFoundException(String message, Throwable cause) {
    super(null, statusCode, message, cause);
  }

  public DtoNotFoundException(Throwable cause) {
    super(null, statusCode, cause);
  }
}
