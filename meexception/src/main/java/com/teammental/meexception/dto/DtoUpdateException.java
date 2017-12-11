package com.teammental.meexception.dto;

import com.teammental.mecore.dto.Dto;

public class DtoUpdateException extends DtoCrudException {

  private static final int statusCode = 403;

  public DtoUpdateException(Dto dto) {
    super(dto, statusCode);
  }

  public DtoUpdateException(Dto dto, String message) {
    super(dto, statusCode, message);
  }

  public DtoUpdateException(Dto dto, String message, Throwable cause) {
    super(dto, statusCode, message, cause);
  }

  public DtoUpdateException(Dto dto, Throwable cause) {
    super(dto, statusCode, cause);
  }
}
