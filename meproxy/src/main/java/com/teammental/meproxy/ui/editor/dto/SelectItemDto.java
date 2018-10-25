package com.teammental.meproxy.ui.editor.dto;

import com.teammental.mecore.stereotype.dto.Dto;

public class SelectItemDto implements Dto {

  private String id;
  private String text;
  private boolean selected;
  private boolean disabled;

  public SelectItemDto() {

  }

  /**
   * Constructor.
   *
   * @param id   id
   * @param text text
   */
  public SelectItemDto(String id, String text) {

    this.id = id;
    this.text = text;
  }

  public String getId() {

    return id;
  }

  public void setId(String id) {

    this.id = id;
  }

  public String getText() {

    return text;
  }

  public void setText(String text) {

    this.text = text;
  }

  public boolean isSelected() {

    return selected;
  }

  public void setSelected(boolean selected) {

    this.selected = selected;
  }

  public boolean isDisabled() {

    return disabled;
  }

  public void setDisabled(boolean disabled) {

    this.disabled = disabled;
  }
}
