package com.teammental.meproxy.ui.editor.dto;

public class SelectSingleFillByTriggerAjaxEditorFieldDto extends EditorFieldDto {

  private String ajaxUrl;

  private String[] triggeredBy;

  private Boolean enableAjaxSearch;

  public String getAjaxUrl() {

    return ajaxUrl;
  }

  public void setAjaxUrl(String ajaxUrl) {

    this.ajaxUrl = ajaxUrl;
  }

  /**
   * Get triggeredBy.
   *
   * @return array
   */
  public String[] getTriggeredBy() {
    if (triggeredBy == null) {
      return null;
    }
    return triggeredBy.clone();
  }

  /**
   * Set triggeredBy.
   *
   * @param triggeredBy array
   */
  public void setTriggeredBy(String[] triggeredBy) {
    if (triggeredBy == null) {
      return;
    }
    this.triggeredBy = triggeredBy.clone();
  }

  public Boolean getEnableAjaxSearch() {

    return enableAjaxSearch;
  }

  public void setEnableAjaxSearch(Boolean enableAjaxSearch) {

    this.enableAjaxSearch = enableAjaxSearch;
  }
}
