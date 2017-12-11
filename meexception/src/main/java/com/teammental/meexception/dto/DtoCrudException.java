package com.teammental.meexception.dto;

import com.teammental.mecore.dto.Dto;
import com.teammental.meexception.RestfulException;

public class DtoCrudException extends RestfulException {

  protected Dto dto;

  /**
   * New DtoCrudException.
   * @param dto a {@link com.teammental.mecore.dto.Dto Dto} object can be included.
   */
  public DtoCrudException(Dto dto) {

    super(500, dto, null, null);
    this.dto = dto;
  }

  /**
   * New DtoCrudException.
   * @param dto a {@link com.teammental.mecore.dto.Dto Dto} object can be included.
   * @param statusCode HttpStatus code which will be used in Rest response.
   */
  public DtoCrudException(Dto dto, int statusCode) {

    super(statusCode, dto, null, null);
    this.dto = dto;
  }

  /**
   * New DtoCrudException.
   * @param dto a {@link com.teammental.mecore.dto.Dto Dto} object can be included.
   * @param statusCode HttpStatus code which will be used in Rest response.
   * @param message Exception message.
   */
  public DtoCrudException(Dto dto, int statusCode, String message) {

    super(statusCode, dto, message, null);
    this.dto = dto;
  }

  /**
   * New DtoCrudException.
   * @param dto a {@link com.teammental.mecore.dto.Dto Dto} object can be included.
   * @param statusCode HttpStatus code which will be used in Rest response.
   * @param message Exception message.
   * @param cause a {@link java.lang.Throwable} object, cause for this exception.
   */
  public DtoCrudException(Dto dto, int statusCode, String message, Throwable cause) {

    super(statusCode, dto, message, cause);
    this.dto = dto;
  }

  /**
   * New DtoCrudException.
   * @param dto a {@link com.teammental.mecore.dto.Dto Dto} object can be included.
   * @param statusCode HttpStatus code which will be used in Rest response.
   * @param cause a {@link java.lang.Throwable} object, cause for this exception.
   */
  public DtoCrudException(Dto dto, int statusCode, Throwable cause) {

    super(statusCode, dto, null, cause);
    this.dto = dto;
  }
}
