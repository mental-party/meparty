package com.teammental.meproxy.ui.editor.dto;

import com.teammental.mecore.stereotype.dto.Dto;

public class EditorFieldDto implements Dto {

  private String templateName;
  private String fieldName;
  private Object inputValue;
  private String attributes;

  public String getTemplateName() {

    return templateName;
  }

  public void setTemplateName(String templateName) {

    this.templateName = templateName;
  }

  public String getFieldName() {

    return fieldName;
  }

  public void setFieldName(String fieldName) {

    this.fieldName = fieldName;
  }

  public Object getInputValue() {

    return inputValue;
  }

  public void setInputValue(Object inputValue) {

    this.inputValue = inputValue;
  }

  public String getAttributes() {

    return attributes;
  }

  public void setAttributes(String attributes) {

    this.attributes = attributes;
  }

}
