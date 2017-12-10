package com.teammental.exception.dto;

import com.teammental.core.dto.Dto;
import com.teammental.exception.RestfulException;

public class DtoCrudException extends RestfulException {

  protected Dto dto;

  public DtoCrudException(Dto dto) {

    super(500, dto, null, null);
    this.dto = dto;
  }

  public DtoCrudException(Dto dto, int statusCode) {

    super(statusCode, dto, null, null);
    this.dto = dto;
  }

  public DtoCrudException(Dto dto, int statusCode, String message) {

    super(statusCode, dto, message, null);
    this.dto = dto;
  }

  public DtoCrudException(Dto dto, int statusCode, String message, Throwable cause) {

    super(statusCode, dto, message, cause);
    this.dto = dto;
  }

  public DtoCrudException(Dto dto, int statusCode, Throwable cause) {

    super(statusCode, dto, null, cause);
    this.dto = dto;
  }
}
