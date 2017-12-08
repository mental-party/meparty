package com.teammental.exception.dto;

public class DtoDeleteException extends DtoCrudException {

  private static final int statusCode = 403;

  public DtoDeleteException() {
    super(null, statusCode);
  }

  public DtoDeleteException(String message) {
    super(null, statusCode, message);
  }

  public DtoDeleteException(String message, Throwable cause) {
    super(null, statusCode, message, cause);
  }

  public DtoDeleteException(Throwable cause) {
    super(null, statusCode, cause);
  }
}
