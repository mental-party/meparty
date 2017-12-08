package com.teammental.exception.dto;

import com.teammental.core.dto.Dto;
import com.teammental.exception.RestfulException;

public class DtoCrudException extends RestfulException {

  protected Dto dto;

  public DtoCrudException(Dto dto) {
    super(500);
    this.dto = dto;
  }

  public DtoCrudException(Dto dto, int statusCode) {
    super(statusCode);
    this.dto = dto;
  }

  public DtoCrudException(Dto dto, int statusCode, String message) {
    super(statusCode, message);
    this.dto = dto;
  }

  public DtoCrudException(Dto dto, int statusCode, String message, Throwable cause) {
    super(statusCode, message, cause);
    this.dto = dto;
  }

  public DtoCrudException(Dto dto, int statusCode, Throwable cause) {
    super(statusCode, cause);
    this.dto = dto;
  }
}
