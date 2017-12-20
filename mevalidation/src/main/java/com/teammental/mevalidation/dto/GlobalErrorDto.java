package com.teammental.mevalidation.dto;

import com.teammental.mecore.stereotype.dto.Dto;

public class GlobalErrorDto implements Dto {
  private String message;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
