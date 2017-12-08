package com.teammental.exception.dto;

import com.teammental.core.dto.Dto;

public class DtoCreateException extends DtoCrudException {

  private static final int statusCode = 403;

  public DtoCreateException(Dto dto) {
    super(dto, statusCode);
  }

  public DtoCreateException(Dto dto, String message) {
    super(dto, statusCode, message);
  }

  public DtoCreateException(Dto dto, String message, Throwable cause) {
    super(dto, statusCode, message, cause);
  }

  public DtoCreateException(Dto dto, Throwable cause) {
    super(dto, statusCode, cause);
  }
}
