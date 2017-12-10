package com.teammental.dto.validation;

import com.teammental.core.dto.Dto;

public class GlobalErrorDto implements Dto {
  private String message;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
