package com.teammental.exception;

import com.teammental.core.exception.Exception;

public class RestfulException extends java.lang.Exception implements Exception {
  private int statusCode;

  public RestfulException(int statusCode) {
    super();
    this.statusCode = statusCode;
  }

  public RestfulException(int statusCode, String message) {
    super(message);
    this.statusCode = statusCode;
  }

  public RestfulException(int statusCode, String message, Throwable cause) {
    super(message, cause);
    this.statusCode = statusCode;
  }

  public RestfulException(int statusCode, Throwable cause) {
    super(cause);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
