package com.teammental.meproxy.ui.editor.dto;

public class SelectSingleFillFromAjaxEditorFieldDto extends EditorFieldDto {

  private String ajaxUrl;

  private Boolean enableAjaxSearch;

  private String placeHolder;

  public String getAjaxUrl() {

    return ajaxUrl;
  }

  public void setAjaxUrl(String ajaxUrl) {

    this.ajaxUrl = ajaxUrl;
  }

  public Boolean getEnableAjaxSearch() {

    return enableAjaxSearch;
  }

  public void setEnableAjaxSearch(Boolean enableAjaxSearch) {

    this.enableAjaxSearch = enableAjaxSearch;
  }

  public String getPlaceHolder() {

    return placeHolder;
  }

  public void setPlaceHolder(String placeHolder) {

    this.placeHolder = placeHolder;
  }
}
